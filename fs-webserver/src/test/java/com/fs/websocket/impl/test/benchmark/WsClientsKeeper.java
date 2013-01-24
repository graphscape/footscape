/**
 *  Jan 24, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fs.commons.api.lang.FsException;
import com.fs.webserver.impl.test.mock.MockWSC;

/**
 * @author wuzhen
 * 
 */
public class WsClientsKeeper {

	private List<MockWSC> clientList;

	private int next;

	private boolean sendReadyMessageAtConnection = false;

	private URI uri;

	private ExecutorService executor;

	public WsClientsKeeper() {
		try {
			uri = new URI("ws://localhost:8080/wsa/testws");
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}
		this.executor = Executors.newCachedThreadPool();
		this.clientList = new ArrayList<MockWSC>();
		this.clientList = Collections.synchronizedList(this.clientList);

	}

	public int size() {
		return this.clientList.size();
	}

	public synchronized MockWSC getFirstClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(0);
		}
	}

	public synchronized MockWSC getLastClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(this.clientList.size() - 1);
		}
	}

	public MockWSC getRandomClient() {
		synchronized (this.clientList) {

			if (this.size() == 0) {
				return null;
			}
			int idx = new Random().nextInt(this.size() - 1);
			return this.clientList.get(idx);
		}
	}

	public void removeRandomClient(boolean close) {
		MockWSC c = this.getRandomClient();
		this.removeClient(c, close);
	}

	public MockWSC createClient(boolean connect) {
		return this.createClient(connect, false);
	}

	public MockWSC createClient(boolean connect, boolean session) {

		final MockWSC client = new MockWSC("client-" + next, uri, this.sendReadyMessageAtConnection);
		this.clientList.add(client);
		if (connect) {
			client.connect();
		}
		if (session) {
			if (!connect) {
				throw new FsException("must connect firstly before session");
			}
			client.createSession();
		}

		this.next++;
		return client;
	}

	public void removeClient(boolean close) {
		MockWSC c = this.getFirstClient();
		this.removeClient(c, close);
	}

	public void removeClient(MockWSC client, boolean close) {
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

}
