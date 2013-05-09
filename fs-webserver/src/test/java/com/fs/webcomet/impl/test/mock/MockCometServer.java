/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.test.mock;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.client.BClient.KeepLiveI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.struct.Path;
import com.fs.webcomet.api.CometFactoryI;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.support.CometListenerAdaptor;
import com.fs.webcomet.api.support.ManagerCometListener;

/**
 * @author wuzhen
 * 
 */
public class MockCometServer extends ManagerCometListener {

	private static final Logger LOG = LoggerFactory.getLogger(MockCometServer.class);

	protected ContainerI container;

	protected CodecI codec;

	protected MessageServiceI engine;

	public MockCometServer(String protocol, String manager, ContainerI c) {
		this(protocol, manager, c, false);
	}

	public MockCometServer(String protocol, String manager, ContainerI c, boolean srmac) {
		super(c.find(CometFactoryI.class, true), protocol, manager);
		this.container = c;
		this.codec = c.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
		MessageServiceI.FactoryI mf = c.find(MessageServiceI.FactoryI.class, true);
		this.engine = mf.create("mock-ws-server");
		this.addHandler(Path.valueOf("/client-is-ready"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockCometServer.this.clientIsReady(sc);
			}
		});

		this.addHandler(Path.valueOf("/echo"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockCometServer.this.echo(sc);
			}
		});
		this.addHandler(KeepLiveI.PATH, true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				LOG.debug("keepLive:" + sc.getRequest());
			}
		});
	}

	/**
	 * @param sc
	 */
	protected void clientIsReady(MessageContext sc) {
		MockMessageWrapper mm = MockMessageWrapper.valueOf(sc.getRequest());
		String wsId = mm.getWsId(true);
		MockMessageWrapper rt = MockMessageWrapper.valueOf("/server-is-ready", null);
		rt.setHeader("wsId", wsId);
		this.sendMessage(wsId, rt);

	}

	/**
	 * @param sc
	 */
	protected void echo(MessageContext sc) {
		MockMessageWrapper mm = MockMessageWrapper.valueOf(sc.getRequest());
		String wsId = mm.getWsId(true);
		String text = mm.getText();
		MockMessageWrapper rt = MockMessageWrapper.valueOf("/echo-from-server", text);
		this.sendMessage(wsId, rt);

	}

	public void addHandler(Path p, boolean strict, MessageHandlerI mh) {
		this.engine.getDispatcher().addHandler(p, strict, mh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.websocket.api.WSListenerI#onMessage(com.fs.webserver
	 * .api.websocket.WebSocketI, java.lang.String)
	 */
	@Override
	public void onMessage(CometI ws, String msgS) {
		super.onMessage(ws, msgS);
		LOG.debug("server received message:" + msgS);
		JSONArray ser = (JSONArray) JSONValue.parse(msgS);
		MessageI msg = (MessageI) this.codec.decode(ser);

		msg.setHeader("wsId", ws.getId());
		ResponseI res = this.engine.service(msg);
		res.assertNoError();

	}

	public void sendMessage(String wsId, MessageI msg) {

		CometI toWs = this.manager.getSocket(wsId, true);
		JSONArray jso = (JSONArray) this.codec.encode(msg);
		String msgS = jso.toJSONString();
		toWs.sendMessage(msgS);//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		super.onException(ws, t);
		// LOG.debug("onConnect,ws:" + ws);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		super.onConnect(ws);
		// LOG.debug("onConnect,ws:" + ws);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		super.onClose(ws, statusCode, reason);
		LOG.debug("onClose of ws:" + ws + ",statusCode:" + statusCode + ",reason:" + reason);
	}

	/**
	 * Client cause the close event is raise in server side.
	 */
	public void waitClientClose() {
		final Semaphore s = new Semaphore(0);

		this.manager.addListener(new CometListenerAdaptor() {

			@Override
			public void onClose(CometI ws, int statusCode, String reason) {
				List<CometI> sL = MockCometServer.this.manager.getSocketList();
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
