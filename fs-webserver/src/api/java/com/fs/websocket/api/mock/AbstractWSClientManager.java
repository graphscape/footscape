/**
 *  Jan 24, 2013
 */
package com.fs.websocket.api.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractWSClientManager<T extends WSClient> extends WSClientManager<T> {

	private List<T> clientList;

	private int next;

	protected URI uri;

	public AbstractWSClientManager() {
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

	protected abstract T newClient(int idx);

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

}
