/**
 *  Dec 12, 2012
 */
package com.fs.websocket.impl.mock;

import java.net.URI;
import java.util.concurrent.Future;
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

	public MockWSClientImpl(String name, URI uri) {
		this.name = name;
		this.uri = uri;
		this.client = new WebSocketClient();
	}

	@Override
	public void onWebSocketText(String message) {
		LOG.info(this.name + ".onWebSocketText,message:" + message);
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
	public void sendMessage(String msg) {
		if (this.connection == null) {
			throw new FsException("not connected");
		}
		this.connection.write(msg);
	}

	@Override
	public WSClient connect() {
		try {
			this.client.start();
			this.connected = new Semaphore(0);
			Future<Session> sf = this.client.connect(this, this.uri);

			this.session = sf.get(10, TimeUnit.SECONDS);
			this.connected.acquireUninterruptibly();

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
		} catch (Exception e) {
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

	protected void onCloseException(Exception e) {
		throw FsException.toRtE(e);

	}

}
