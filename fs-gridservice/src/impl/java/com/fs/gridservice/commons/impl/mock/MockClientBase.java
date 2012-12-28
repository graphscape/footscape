/**
 *  Dec 12, 2012
 */
package com.fs.gridservice.commons.impl.mock;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.websocket.RemoteEndpoint;

import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.api.WebSocketConnection;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.WebSocketClientFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.gridservice.commons.api.gobject.WebSocketGoI;
import com.fs.gridservice.commons.api.mock.MockClient;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public abstract class MockClientBase extends MockClient implements
		WebSocketListener {
	private static final Logger LOG = LoggerFactory
			.getLogger(MockClientBase.class);

	protected URI uri;

	protected WebSocketClient client;

	protected BlockingQueue<MessageI> messageReceived;

	protected WebSocketConnection connection;

	protected Semaphore connected;

	protected String clientId;

	protected String terminalId;

	protected String accountId;

	protected String sessionId;

	protected CodecI messageCodec;

	public MockClientBase(WebSocketClientFactory f, ContainerI c, URI uri) {
		this.uri = uri;
		this.messageReceived = new LinkedBlockingQueue<MessageI>();
		this.messageCodec = c.find(CodecI.FactoryI.class, true).getCodec(
				MessageI.class);
		this.client = f.newWebSocketClient(this);

	}

	@Override
	public MockClient auth(String accId) {
		try {
			this.connect().get();
			this.ready(1000, TimeUnit.SECONDS).get();
			//
			this.binding(accId);

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return this;
	}

	@Override
	public void sendMessage(MessageI msg) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}

		RemoteEndpoint<Object> endpoint = this.client.getWebSocket()
				.getSession().getRemote();
		try {
			JSONArray jsm = (JSONArray) this.messageCodec.encode(msg);//
			String code = jsm.toJSONString();
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
		LOG.info("client,onWebSocketText,msg:" + message);
		JSONArray ser = (JSONArray) JSONValue.parse(message);
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
	public Future<MockClientBase> connect() {
		FutureTask<MockClientBase> rt = new FutureTask<MockClientBase>(
				new Callable<MockClientBase>() {

					@Override
					public MockClientBase call() throws Exception {
						MockClientBase.this.syncConnect();
						return MockClientBase.this;
					}
				});
		rt.run();
		return rt;

	}

	public Future<MockClientBase> ready(final long time, final TimeUnit tu) {
		FutureTask<MockClientBase> rt = new FutureTask<MockClientBase>(
				new Callable<MockClientBase>() {

					@Override
					public MockClientBase call() throws Exception {
						MockClientBase.this.syncReady(time, tu);
						return MockClientBase.this;
					}
				});
		rt.run();
		return rt;

	}

	public void syncReady(final long time, final TimeUnit tu) throws Exception {

		MessageI msg = this.messageReceived.poll(time, tu);
		if (msg == null) {
			throw new FsException("timeout for waiting server ready.");
		}

		String path = msg.getPath();

		if (!path.equals(WebSocketGoI.P_READY)) {
			throw new FsException("first message must by"
					+ WebSocketGoI.P_READY + ", but got:" + path);
		}
		this.terminalId = msg.getString("terminalId", true);
		this.clientId = msg.getString("clientId", true);
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

	@Override
	public String getTerminalId() {
		return terminalId;
	}

	@Override
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

	@Override
	public Future<MessageI> receiveMessage() {
		FutureTask<MessageI> rt = new FutureTask<MessageI>(
				new Callable<MessageI>() {

					@Override
					public MessageI call() throws Exception {
						return MockClientBase.this.messageReceived.take();

					}
				});
		rt.run();
		return rt;
	}

	public void binding(String accId) {
		if (this.clientId == null) {
			throw new FsException("not ready yet");
		}

		MessageI msg = new MessageSupport() {
		};
		msg.setHeader(MessageI.HK_PATH, "/terminal/auth");
		msg.setPayload("accountId", accId);
		msg.setPayload("password", "TODO");
		this.sendMessage(msg);
		MessageI res = null;

		try {
			res = this.receiveMessage().get(1000000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException e) {
			throw new FsException(e);
		} catch (TimeoutException e) {
			throw new FsException("timeout for auth :" + accountId);
		}
		String path = res.getPath();
		if (path.endsWith("terminal/auth/success")) {
			String sid = (String) res.getPayload("sessionId", true);
			this.sessionId = sid;
			this.accountId = accId;//
			return;
		} else {
			throw new FsException("failed binding accId:" + accId + ",path:"
					+ path);
		}
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

}
