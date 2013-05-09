/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 27, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import java.net.URI;

import com.fs.commons.api.client.BClientFactoryI;
import com.fs.webcomet.impl.test.mock.MockCometBClient;
import com.fs.webcomet.impl.test.mock.MockCometServer;
import com.fs.websocket.api.mock.WSClientRunner;

/**
 * @author wu
 * 
 */
public abstract class WebSocketWSClientRunner extends WSClientRunner<MockCometBClient> {

	private MockCometServer server;

	public WebSocketWSClientRunner(BClientFactoryI.ProtocolI pro, URI uri, int initClients, int maxCon,
			int maxEffort, int duration) {
		this(pro, uri, MockCometBClient.class, initClients, maxCon, maxEffort, duration);

	}

	public WebSocketWSClientRunner(BClientFactoryI.ProtocolI pro, URI uri,
			Class<? extends MockCometBClient> wcls, int initClients, int cc, int max, int duration) {
		super(pro, uri, wcls, initClients, cc, max, duration);
	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {

		// NOTE,super.init may connect, so the server must stared before this.
		super.init();

		this.server = new MockCometServer("websocket", "testws", this.container);
		this.server.start();
	}

}
