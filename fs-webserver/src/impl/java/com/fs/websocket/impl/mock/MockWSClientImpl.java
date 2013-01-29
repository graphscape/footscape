/**
 *  Dec 12, 2012
 */
package com.fs.websocket.impl.mock;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.io.ChannelEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketConnection;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.LogicalConnection;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.common.io.AbstractWebSocketConnection;
import org.eclipse.jetty.websocket.common.io.IOState;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;
import com.fs.websocket.api.mock.WSClient;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockWSClientImpl extends WSClient implements WebSocketListener {
	private static final Logger LOG = LoggerFactory.getLogger(MockWSClientImpl.class);

	protected URI uri;

	protected WebSocketClient client;

	protected WebSocketConnection connection;

	protected Semaphore connected;

	protected Semaphore closed;

	protected Session session;

	protected String name;

	protected MessageServiceI engine;

	protected CodecI codec;

	protected long lastSendMessageTs;

	public MockWSClientImpl(String name, URI uri, MessageServiceI ms, CodecI codec) {
		this.name = name;
		this.uri = uri;
		this.engine = ms;
		this.codec = codec;
		this.client = new WebSocketClient();
	}

	@Override
	public void onWebSocketText(String message) {
		LOG.info(this.name + ".onWebSocketText,message:" + message);
		try {
			JSONArray ser = (JSONArray) JSONValue.parse(message);
			MessageI msg = (MessageI) this.codec.decode(ser);
			ResponseI res = this.engine.service(msg);

			res.assertNoError();
		} catch (Throwable t) {
			LOG.error("onWebSocketText error", t);
		}
	}

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		LOG.info(payload + "," + offset + "," + len);
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		LOG.info(this.name + " closed:" + statusCode + "," + reason);
		if (this.closed != null) {
			this.closed.release();
		} else {// closing for other reason?
			LOG.warn("client closed for some other reason(may close from server),there is no one to wait this event.");
		}

	}

	@Override
	public void onWebSocketConnect(WebSocketConnection connection) {
		LOG.info(this.name + " onWebSocketConnect");
		this.connection = connection;
		this.connected.release();
	}

	@Override
	public void onWebSocketException(WebSocketException error) {
		LOG.error(this.name + " onWebSocketException", error);
	}

	@Override
	public void sendMessage(MessageI msg) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}

		JSONArray jsm = (JSONArray) this.codec.encode(msg);//
		String code = jsm.toJSONString();
		this.connection.write(code);
		this.lastSendMessageTs = System.currentTimeMillis();

	}

	@Override
	public WSClient connect() {
		try {
			this.client.start();
			this.connected = new Semaphore(0);
			Future<Session> sf = this.client.connect(this, this.uri);

			this.session = sf.get(10, TimeUnit.SECONDS);
			if (this.session == null) {
				throw new FsException("timeout to get session for connect");
			}

			if (!this.connected.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException(this.name + "timeout to wait the connected signal");
			}

		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
		return this;
	}

	@Override
	public void close() {
		this.closed = new Semaphore(0);

		try {

			this.connection.close();

		} catch (Throwable e) {
			this.onCloseException(e);
		}

		try {
			this.closed.acquire();
		} catch (InterruptedException e1) {
			throw new FsException(e1);
		}

		try {

			// TODO replace with timeout for avoiding deadlock
			// should not call this.client.stop(),for that case the onCloseEvent
			// will not notified.
			this.client.stop();
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	protected void onCloseException(Throwable e) {
		WebSocketSession wss = (WebSocketSession) this.connection;

		LogicalConnection lc = wss.getConnection();
		IOState iso = lc.getIOState();
		iso.getConnectionState();

		AbstractWebSocketConnection wsc = (AbstractWebSocketConnection) lc;

		EndPoint ep = wsc.getEndPoint();
		ChannelEndPoint cep = (ChannelEndPoint) ep;
		// cep.isOpen();

		throw new FsException("close error for client:" + this.name + ",[iostate,state:" + iso.getState()
				+ ",isInputClosed:" + iso.isInputClosed() + ",isOutputClosed:" + iso.isOutputClosed() + ","
				+ " ],[,channelEndpoint:,isopen:" + cep.isOpen() + ",isInputShutdown:"
				+ cep.isInputShutdown() + "," + cep.isOutputShutdown() + ",]", e);

	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public String getName() {
		//
		return this.name;
	}

	/*
	 * Jan 26, 2013
	 */
	@Override
	public void addHandler(Path p, HandlerI<MessageContext> mh) {
		this.addHandler(p, false, mh);
	}

	@Override
	public void addHandler(Path p, boolean strict, HandlerI<MessageContext> mh) {
		this.engine.getDispatcher().addHandler(p, strict, mh);
	}

	@Override
	public int getIdleTime() {
		long now = System.currentTimeMillis();
		return (int) (now - this.lastSendMessageTs);
	}

}
