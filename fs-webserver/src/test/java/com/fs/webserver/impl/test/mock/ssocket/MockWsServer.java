/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock.ssocket;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.webserver.impl.test.mock.MockMessage;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.support.AbstractWsListener;
import com.fs.websocket.api.support.ManagerWsListener;

/**
 * @author wuzhen
 * 
 */
public class MockWsServer extends ManagerWsListener {

	private static final Logger LOG = LoggerFactory.getLogger(MockWsServer.class);

	protected ContainerI container;

	public MockWsServer(String manager, ContainerI c) {
		super(c.find(WsFactoryI.class, true), manager);
		this.container = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WSListenerI#onMessage(com.fs.webserver
	 * .api.websocket.WebSocketI, java.lang.String)
	 */
	@Override
	public void onMessage(WebSocketI ws, String msg) {
		super.onMessage(ws, msg);
		MockMessage mm = MockMessage.parse(msg);

		String toSid = mm.getTo();
		WebSocketI toWs = this.manager.getSocket(toSid, true);
		toWs.sendMessage(msg);//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		super.onException(ws, t);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		super.onConnect(ws);
		String sid = ws.getId();// sessionId
		ws.sendMessage("server,client," + sid);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		super.onClose(ws, statusCode, reason);

	}

	public void waitClientClose() {
		final Semaphore s = new Semaphore(0);

		this.manager.addListener(new AbstractWsListener() {

			@Override
			public void onClose(WebSocketI ws, int statusCode, String reason) {
				List<WebSocketI> sL = MockWsServer.this.manager.getSocketList();
				if (sL.isEmpty()) {
					s.release();
				}
			}
		});

		if (this.manager.getSocketList().isEmpty()) {
			// no matter the semaphore may released or not,
			return;
		}

		LOG.info("waiting all are closed");
		s.acquireUninterruptibly();//
		LOG.info("done");

	}

	public void getWebSocketList() {
		//
		// return this.manager.getSocket(id);

	}
}
