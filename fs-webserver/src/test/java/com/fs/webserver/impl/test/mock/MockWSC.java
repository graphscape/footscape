/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketConnection;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockWSC implements WebSocketListener {
	private static final Logger LOG = LoggerFactory.getLogger(MockWSC.class);

	protected URI uri;

	protected WebSocketClient client;

	protected BlockingQueue<MockMessage> messageReceived;

	protected WebSocketConnection connection;

	protected Semaphore connected;

	protected Semaphore appSessionGot;

	protected Session session;

	protected String appSessionId;

	protected String name;

	protected ExecutorService executor;

	protected Semaphore serverIsReady;

	public MockWSC(String name, URI uri, boolean serverWillReadyMessage) {
		this.executor = Executors.newCachedThreadPool();
		this.name = name;
		this.uri = uri;
		this.messageReceived = new LinkedBlockingQueue<MockMessage>();
		this.client = new WebSocketClient();
		if (serverWillReadyMessage) {

			this.serverIsReady = new Semaphore(0);
			throw new FsException("not supported,to be discussed later.");
		}
	}

	public void sendMessage(String to, String text) {
		if (this.appSessionId == null) {
			throw new FsException("session not got.");
		}
		this.sendMessage(this.appSessionId, to, text);
	}

	public void sendMessage(String from, String to, String text) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}

		MockMessage mm = new MockMessage(from, to, text);

		this.connection.write(mm.forSending());

	}

	public MockMessage nextMessage(long timeout) {
		try {
			return this.messageReceived.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);//
		}
	}

	@Override
	public void onWebSocketText(String message) {
		LOG.info(this.name + ".onWebSocketText,message:" + message);
		MockMessage ms = MockMessage.parse(message);
		if (this.serverIsReady != null) {// first
											// message
											// must
											// be
											// server
											// is
			// ready.

			ms.assertEquals("server", "client", "server-is-ready");
			this.serverIsReady.release();
			return;

		}

		if (this.appSessionGot != null) {//

			if (!ms.getFrom().equals("server")) {//
				throw new FsException("first message must appSessionId from server");
			}

			this.appSessionId = ms.getText();//
			if (this.appSessionId == null) {
				throw new FsException("session id is null.");
			}

			this.appSessionGot.release();
			return;
		}
		if (!ms.getTo().equals(this.appSessionId)) {
			throw new FsException("message send to error:" + ms);
		}
		this.messageReceived.add(ms);

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

	public void createSession() {

		Future<Object> rt = this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				return MockWSC.this.doCreateSession();
			}
		});

		try {
			rt.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}

	}

	public String doCreateSession() {
		LOG.debug(this.name + " is in acquireSession...");
		this.appSessionGot = new Semaphore(0);
		this.sendMessage("client", "server", "create-session");
		this.appSessionGot.acquireUninterruptibly();
		LOG.debug(this.name + " has done of acquireSession.");
		this.appSessionGot = null;
		return this.appSessionId;
	}

	/**
	 * @param wsc
	 */
	public void connect() {
		try {
			this.client.start();
			this.connected = new Semaphore(0);
			Future<Session> sf = this.client.connect(this, this.uri);

			this.session = sf.get(10, TimeUnit.SECONDS);
			this.connected.acquireUninterruptibly();

			if (this.serverIsReady != null) {
				if (!this.serverIsReady.tryAcquire(10, TimeUnit.SECONDS)) {
					throw new FsException("timeout for waiting server-is-ready");
				}
				this.serverIsReady = null;//
			}
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
	}

	/**
	 * @return
	 */
	public String getSessionId() {
		return this.appSessionId;
	}

	/**
	 * Dec 12, 2012
	 */
	public void close() {
		try {
			this.connection.close();
			this.client.stop();
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

}
