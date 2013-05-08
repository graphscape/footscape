/**
 *  Dec 12, 2012
 */
package com.fs.websocket.impl.mock;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.io.ChannelEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.LogicalConnection;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.common.io.AbstractWebSocketConnection;
import org.eclipse.jetty.websocket.common.io.IOState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.AClientSupport;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockWSClientImpl extends AClientSupport implements WebSocketListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockWSClientImpl.class);

	protected WebSocketClient client;

	protected Session connection;

	protected Semaphore connected;

	protected Semaphore closed;

	protected Session session;

	public MockWSClientImpl(PropertiesI<Object> pts) {
		super(pts);
		this.client = new WebSocketClient();
	}

	@Override
	public void onWebSocketText(String message) {
		super.onMessage(message);
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
	public void onWebSocketConnect(Session connection) {
		LOG.info(this.name + " onWebSocketConnect");
		this.connection = connection;
		this.connected.release();
	}

	@Override
	public void onWebSocketError(Throwable error) {
		LOG.error(this.name + " onWebSocketException", error);
	}

	@Override
	protected void sendMessage(String msg) {
		try {
			this.connection.getRemote().sendString(msg);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	@Override
	public AClientI connect() {
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

}
