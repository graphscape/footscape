/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.impl.test.mock;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.WebSocketClientFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class MockClientFactory {

	protected WebSocketClientFactory cf = new WebSocketClientFactory();

	protected ContainerI container;

	public MockClientFactory(ContainerI c) {
		this.container = c;
	}

	public MockClientFactory start() {
		try {
			cf.start();
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
		return this;
	}
	
	public MockClient newClientAndAuth(String accId) throws Exception {
		//

		URI uri = new URI("ws://localhost:8080/wsa/default");// default
		// wsManager.
		MockClient client = new MockClient(this.cf, this.container, uri);
		client = client.connect().get();
		client.ready(1000, TimeUnit.SECONDS).get();
		//
		client.binding(accId);

		return client;
	}

}
