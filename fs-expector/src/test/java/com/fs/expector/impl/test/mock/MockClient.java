/**
 *  Dec 12, 2012
 */
package com.fs.expector.impl.test.mock;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import javax.net.websocket.RemoteEndpoint;

import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketConnection;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.WebSocketClientFactory;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockClient implements WebSocketListener {
	private static final Logger LOG = LoggerFactory.getLogger(MockClient.class);

	protected URI uri;

	protected WebSocketClient client;

	protected BlockingQueue<MessageI> messageReceived;

	protected WebSocketConnection connection;

	protected Semaphore connected;

	protected String sessionId;

	protected CodecI messageCodec;

	public MockClient(WebSocketClientFactory f, String sid, ContainerI c, URI uri) {
		this.sessionId = sid;
		this.uri = uri;
		this.messageReceived = new LinkedBlockingQueue<MessageI>();
		this.messageCodec = c.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);
		this.client = f.newWebSocketClient(this);

	}

	public void sendMessage(MessageI msg) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}
		if (this.sessionId == null) {
			throw new FsException("session not got.");
		}

		RemoteEndpoint<Object> endpoint = this.client.getWebSocket().getSession().getRemote();
		try {
			JSONValue jsm = (JSONValue) this.messageCodec.encode(msg);//
			String code = JSONValue.toJSONString(jsm);
			endpoint.sendString(code);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	/**
	 * @param wsc
	 */
	public void init(WebSocketClient wsc) {
		this.client = wsc;
	}

	@Override
	public void onWebSocketText(String message) {
		LOG.info("msg:" + message);
		JSONValue ser = (JSONValue) JSONValue.parse(message);
		MessageI msg = (MessageI) this.messageCodec.decode(ser);
		this.messageReceived.add(msg);

	}

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		LOG.info(payload + "," + offset + "," + len);
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		LOG.info("closed:" + statusCode + "," + reason);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jetty.websocket.api.WebSocketListener#onWebSocketConnect(
	 * org.eclipse.jetty.websocket.api.WebSocketConnection)
	 */
	@Override
	public void onWebSocketConnect(WebSocketConnection connection) {
		this.connection = connection;
		this.connected.release();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jetty.websocket.api.WebSocketListener#onWebSocketException
	 * (org.eclipse.jetty.websocket.api.WebSocketException)
	 */
	@Override
	public void onWebSocketException(WebSocketException error) {
		LOG.error("", error);
	}

	/**
	 * @return
	 */
	public Future<MockClient> connect() {
		FutureTask<MockClient> rt = new FutureTask<MockClient>(new Callable<MockClient>() {

			@Override
			public MockClient call() throws Exception {
				MockClient.this.syncConnect();
				return MockClient.this;
			}
		});
		rt.run();
		return rt;

	}

	public void syncConnect() {
		try {
			this.connected = new Semaphore(0);
			FutureCallback<UpgradeResponse> fc = this.client.connect(this.uri);
			this.connected.acquireUninterruptibly();

		} catch (IOException e) {
			throw FsException.toRtE(e);//
		}
	}

	/**
	 * @return
	 */
	public String getSessionId() {
		return this.sessionId;
	}

	/**
	 * Dec 12, 2012
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	public Future<MessageI> receiveMessage() {
		FutureTask<MessageI> rt = new FutureTask<MessageI>(new Callable<MessageI>() {

			@Override
			public MessageI call() throws Exception {
				return MockClient.this.messageReceived.take();

			}
		});
		return rt;
	}

	/**
	 * Dec 16, 2012
	 */
	public void binding(String sid) throws Exception {
		MessageI msg = new MessageSupport() {
		};
		msg.setHeader("type", "action");
		msg.setHeader("action", "binding");
		this.sendMessage(msg);

		MessageI res = this.receiveMessage().get();
		String status = res.getHeader("status", true);
		String reason = res.getHeader("reason", true);
		if (status.equals("success")) {
			return;
		} else {
			throw new FsException("failed binding sessionId:" + sid + "," + reason);
		}
	}

}
