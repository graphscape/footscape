/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 25, 2013
 */
package com.fs.commons.impl.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClient;
import com.fs.commons.api.client.BClient.KeepLiveI;
import com.fs.commons.api.client.BClientFactoryI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.support.AClientSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class BClientManagerImpl<T extends BClient> implements BClientFactoryI<T> {

	private List<T> clientList;

	private int next;

	protected Map<String, URI> uriMap;

	protected Map<String, ProtocolI> protocolMap;

	protected Class<? extends T> wcls;

	protected ContainerI container;

	protected CodecI codec;

	protected MessageServiceI.FactoryI factory;

	protected KeepLiveI keepLive;

	public BClientManagerImpl() {
		this.clientList = new ArrayList<T>();
		this.clientList = Collections.synchronizedList(this.clientList);
		this.keepLive = new BClientKeepLive();
		this.uriMap = new HashMap<String, URI>();
		this.protocolMap = new HashMap<String, ProtocolI>();
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

	@Override
	public T createClient(String prot, boolean connect) {
		return this.createClient(prot, connect, new MapProperties<Object>());
	}

	@Override
	public T createClient(String prot, boolean connect, PropertiesI<Object> pts) {

		final T client = this.newClient(this.next++, prot, pts);
		client.keepLive(this.keepLive);
		if (connect) {
			client.connect();
		}
		//
		this.clientList.add(client);
		return client;
	}

	protected T newClient(int idx, String prot, PropertiesI<Object> pts) {
		String name = "client-" + idx;
		MessageServiceI engine = this.factory.create(name);
		pts.setProperty(AClientSupport.PK_CODEC, this.codec);
		pts.setProperty(AClientSupport.PK_ENGINE, engine);
		pts.setProperty(AClientSupport.PK_NAME, name);

		ProtocolI pro = this.protocolMap.get(prot);
		if (pro == null) {
			throw new FsException("no this protocol:" + prot + ",all:" + this.protocolMap.keySet());
		}
		URI uri = this.uriMap.get(prot);
		if (uri == null) {
			throw new FsException("no uri for protocol:" + prot);
		}
		pts.setProperty(AClientSupport.PK_URI, uri);
		AClientI client = ClassUtil.newInstance(pro.getClientClass(), new Class[] { PropertiesI.class },
				new Object[] { pts });
		T rt = ClassUtil.newInstance(this.wcls, new Class[] { AClientI.class, PropertiesI.class },
				new Object[] { client, pts });

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
	public void init(Class<? extends T> wcls, ContainerI c) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.client.BClientFactoryI#registerProtocol(com.fs.commons
	 * .api.client.BClientFactoryI.ProtocolI, java.net.URI)
	 */
	@Override
	public void registerProtocol(BClientFactoryI.ProtocolI pro, URI uri) {
		this.protocolMap.put(pro.getName(), pro);
		this.uriMap.put(pro.getName(), uri);
	}


}
