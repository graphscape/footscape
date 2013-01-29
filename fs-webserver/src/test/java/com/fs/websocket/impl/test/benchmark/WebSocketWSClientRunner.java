/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 27, 2013
 */
package com.fs.websocket.impl.test.benchmark;

import java.net.URI;

import com.fs.webserver.impl.test.mock.MockWSClientWrapper;
import com.fs.webserver.impl.test.mock.ssocket.MockWsServer;
import com.fs.websocket.api.mock.WSClientRunner;
import com.fs.websocket.impl.test.WebSocketTestSPI;

/**
 * @author wu
 * 
 */
public abstract class WebSocketWSClientRunner extends WSClientRunner<MockWSClientWrapper> {

	private MockWsServer server;

	/**
	 * @param uri
	 * @param wcls
	 * @param cc
	 * @param max
	 * @param rate
	 * @param duration
	 */
	public WebSocketWSClientRunner(int initClients, int maxCon, int maxEffort, int duration) {
		this(WebSocketTestSPI.TEST_WS_URI, MockWSClientWrapper.class, initClients, maxCon, maxEffort,
				duration);

	}

	public WebSocketWSClientRunner(URI uri, Class<? extends MockWSClientWrapper> wcls, int initClients,
			int cc, int max, int duration) {
		super(uri, wcls, initClients, cc, max, duration);
	}

	/*
	 * Jan 27, 2013
	 */
	@Override
	public void init() {

		// NOTE,super.init may connect, so the server must stared before this.
		super.init();

		this.server = new MockWsServer("testws", this.container);
		this.server.start();
	}

}
