/**
 *  Jan 24, 2013
 */
package com.fs.gridservice.commons.impl.test.benchmark;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.MockClientFactory;

/**
 * @author wuzhen
 * 
 */
public class WsClientsKeeper {

	private List<MockClient> clientList;

	private int next;

	private boolean sendReadyMessageAtConnection = false;

	private URI uri;

	private ExecutorService executor;

	private MockClientFactory factory;

	public WsClientsKeeper(ContainerI c) {
		this(MockClientFactory.getInstance(c));
	}

	public WsClientsKeeper(MockClientFactory cf) {
		try {
			uri = new URI("ws://localhost:8080/wsa/testws");
		} catch (URISyntaxException e) {
			throw new FsException(e);
		}
		this.factory = cf;
		this.executor = Executors.newCachedThreadPool();
		this.clientList = new ArrayList<MockClient>();
		this.clientList = Collections.synchronizedList(this.clientList);

	}

	public int size() {
		return this.clientList.size();
	}

	public synchronized MockClient getFirstClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(0);
		}
	}

	public synchronized MockClient getLastClient() {
		synchronized (this.clientList) {
			if (this.size() == 0) {
				return null;
			}
			return this.clientList.get(this.clientList.size() - 1);
		}
	}

	public MockClient getRandomClient() {
		synchronized (this.clientList) {

			if (this.size() == 0) {
				return null;
			}
			int idx = new Random().nextInt(this.size() - 1);
			return this.clientList.get(idx);
		}
	}

	public void removeRandomClient(boolean close) {
		MockClient c = this.getRandomClient();
		this.removeClient(c, close);
	}

	public MockClient createClient(boolean connect) {
		return this.createClient(connect, null);
	}

	public MockClient createClient(boolean connect, PropertiesI<Object> cred) {

		final MockClient client = this.factory.newClient("client-" + next++);

		if (connect) {
			client.connect();
		}
		if (cred != null) {
			if (!connect) {
				throw new FsException("must connect firstly before session");
			}
			client.auth(cred);
		}
		// not must by ready before add to list.
		this.clientList.add(client);
		return client;
	}

	public void removeClient(boolean close) {
		MockClient c = this.getFirstClient();
		this.removeClient(c, close);
	}

	public void removeClient(MockClient client, boolean close) {
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
