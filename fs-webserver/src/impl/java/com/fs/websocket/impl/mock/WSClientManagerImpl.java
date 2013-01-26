/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 25, 2013
 */
package com.fs.websocket.impl.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.websocket.api.mock.WSClient;
import com.fs.websocket.api.mock.WSClientManager;
import com.fs.websocket.api.mock.WsClientWrapper;

/**
 * @author wu
 * 
 */
public class WSClientManagerImpl<T extends WsClientWrapper> extends WSClientManager<T> {

	private List<T> clientList;

	private int next;

	protected URI uri;

	protected Class<T> wcls;

	protected ContainerI container;

	protected CodecI codec;

	protected MessageServiceI.FactoryI factory;

	public WSClientManagerImpl() {
		try {
			uri = new URI("ws://localhost:8080/wsa/testws");
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}
		this.clientList = new ArrayList<T>();
		this.clientList = Collections.synchronizedList(this.clientList);

	}

	public int size() {
		return this.clientList.size();
	}

	public synchronized T getFirstClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(0);
		}
	}

	public synchronized T getLastClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(this.clientList.size() - 1);
		}
	}

	public T getRandomClient() {
		synchronized (this.clientList) {

			if (this.size() == 0) {
				return null;
			}
			int idx = new Random().nextInt(this.size() - 1);
			return this.clientList.get(idx);
		}
	}

	public void removeRandomClient(boolean close) {
		T c = this.getRandomClient();
		this.removeClient(c, close);
	}

	public T createClient(boolean connect) {

		final T client = this.newClient(this.next++);
		if (connect) {
			client.connect();
		}
		//
		this.clientList.add(client);
		return client;
	}

	protected T newClient(int idx) {
		String name = "client-" + idx;
		MessageServiceI engine = this.factory.create(name);
		WSClient client = new MockWSClientImpl(name, uri, engine, codec);
		T rt = ClassUtil.newInstance(this.wcls, new Class[] { WSClient.class }, new Object[] { client });

		return rt;
	}

	public void removeClient(boolean close) {
		T c = this.getFirstClient();
		this.removeClient(c, close);
	}

	public void removeClient(T client, boolean close) {
		boolean removed = this.clientList.remove(client);
		if (!removed) {
			throw new FsException("no this client:" + client);
		}
		if (close) {
			client.close();
		}
	}

	/**
	 * @return
	 */
	public int total() {
		return this.next;
	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public void init(Class<T> wcls, ContainerI c) {
		this.wcls = wcls;
		this.container = c;
		this.codec = c.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
		this.factory = c.find(MessageServiceI.FactoryI.class, true);
	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public List<T> getClientList() {
		//
		return this.clientList;
	}

}
