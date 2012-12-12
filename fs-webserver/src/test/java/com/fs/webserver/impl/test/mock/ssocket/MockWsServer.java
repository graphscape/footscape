/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock.ssocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.webserver.api.websocket.WebSocketI;
import com.fs.webserver.api.websocket.WsFactoryI;
import com.fs.webserver.api.websocket.WsListenerI;
import com.fs.webserver.api.websocket.WsManagerI;

/**
 * @author wuzhen
 * 
 */
public class MockWsServer implements WsListenerI {

	private static final Logger LOG = LoggerFactory
			.getLogger(MockWsServer.class);

	protected ContainerI container;

	protected WsManagerI manager;

	protected String name;

	public MockWsServer(String manager, ContainerI c) {
		this.container = c;
		this.name = manager;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.webserver.api.websocket.WSListenerI#onSocketOpen(com.fs.webserver
	 * .api.websocket.WebSocketI)
	 */
	@Override
	public void onSocketOpen(WebSocketI ws) {
		LOG.info("onSocketopen,ws:" + ws);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.webserver.api.websocket.WSListenerI#onMessage(com.fs.webserver
	 * .api.websocket.WebSocketI, java.lang.String)
	 */
	@Override
	public void onMessage(WebSocketI ws, String message) {
		LOG.info("onMessage,ws:" + ws + ",msg:" + message);
	}

	public void start() {
		WsFactoryI f = this.container.find(WsFactoryI.class, true);
		manager = f.getManager("testws", true);// see
		// WebServerTestSPI.active()

		manager.addListener(this);
	}
}
