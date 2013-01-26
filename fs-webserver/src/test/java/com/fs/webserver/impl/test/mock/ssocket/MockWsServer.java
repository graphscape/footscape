/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock.ssocket;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.webserver.impl.test.mock.MockMessageWrapper;
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

	protected boolean sendReadyMessageAtConnection;

	protected CodecI codec;

	public MockWsServer(String manager, ContainerI c) {
		this(manager, c, false);
	}

	public MockWsServer(String manager, ContainerI c, boolean srmac) {
		super(c.find(WsFactoryI.class, true), manager);
		this.container = c;
		this.sendReadyMessageAtConnection = srmac;
		this.codec = c.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WSListenerI#onMessage(com.fs.webserver
	 * .api.websocket.WebSocketI, java.lang.String)
	 */
	@Override
	public void onMessage(WebSocketI ws, String msgS) {
		super.onMessage(ws, msgS);
		LOG.debug("server received message:" + msgS);
		JSONArray ser = (JSONArray) JSONValue.parse(msgS);
		MessageI msg = (MessageI) this.codec.decode(ser);

		MockMessageWrapper mm = MockMessageWrapper.valueOf(msg);

		String toSid = mm.getTo();
		if (toSid.equals("server")) {
			this.processToServerMessage(ws, mm);
		} else {
			WebSocketI toWs = this.manager.getSocket(toSid, true);
			this.sendMessage(toWs, msg);
		}

	}

	public void sendMessage(WebSocketI ws, MessageI msg) {
		JSONArray jso = (JSONArray) this.codec.encode(msg);
		String msgS = jso.toJSONString();
		ws.sendMessage(msgS);//

	}

	protected void processToServerMessage(WebSocketI ws, MockMessageWrapper mm) {
		String cmd = mm.getText();
		if ("create-session".equals(cmd)) {
			String sid = ws.getId();//
			String from = "server";
			String to = "client";
			String text = sid;
			MockMessageWrapper msg = MockMessageWrapper.valueOf(from, to, text);
			this.sendMessage(ws, msg);
			// LOG.debug(msg);

		} else {
			throw new FsException("cmd not support:" + cmd + ",for message:" + mm);
		}
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
		if (this.sendReadyMessageAtConnection) {
			this.sendMessage(ws, MockMessageWrapper.valueOf("server", "client", "server-is-ready"));
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		super.onClose(ws, statusCode, reason);
		LOG.debug("onClose of ws:" + ws + ",statusCode:" + statusCode + ",reason:" + reason);
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
