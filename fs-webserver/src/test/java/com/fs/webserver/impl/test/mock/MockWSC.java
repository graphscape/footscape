/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javax.net.websocket.RemoteEndpoint;

import org.eclipse.jetty.util.FutureCallback;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
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

	protected String id;

	public MockWSC(String id, URI uri) {
		this.uri = uri;
		this.id = id;
		this.messageReceived = new LinkedBlockingQueue<MockMessage>();
	}

	public void sendMessage(String to, String text) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}
		MockMessage mm = new MockMessage(this.id, to, text);
		RemoteEndpoint<Object> endpoint = this.client.getWebSocket()
				.getSession().getRemote();
		try {
			endpoint.sendString(mm.forSending());
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	public MockMessage nextMessage(long timeout) {
		try {
			return this.messageReceived.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);//
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
		MockMessage ms = MockMessage.parse(message);
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

	/**
	 * @return
	 */
	public Future<MockWSC> connect() {
		FutureTask<MockWSC> rt = new FutureTask<MockWSC>(
				new Callable<MockWSC>() {

					@Override
					public MockWSC call() throws Exception {
						MockWSC.this.syncConnect();
						return MockWSC.this;
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
	public String getId() {
		return this.id;
	}

}
