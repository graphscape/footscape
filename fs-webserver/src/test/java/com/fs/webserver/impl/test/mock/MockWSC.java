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

import org.eclipse.jetty.io.ChannelEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.common.LogicalConnection;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.common.io.AbstractWebSocketConnection;
import org.eclipse.jetty.websocket.common.io.IOState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.websocket.impl.mock.MockWSClientImpl;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockWSC extends MockWSClientImpl {
	private static final Logger LOG = LoggerFactory.getLogger(MockWSC.class);

	protected BlockingQueue<MockMessage> messageReceived;

	protected Semaphore appSessionGot;

	protected String appSessionId;

	protected ExecutorService executor;

	protected Semaphore serverIsReady;

	public MockWSC(String name, URI uri) {
		super(name, uri);
		this.executor = Executors.newCachedThreadPool();
		this.messageReceived = new LinkedBlockingQueue<MockMessage>();
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
		super.onWebSocketText(message);
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
		this.sendMessage(new MockMessage("client", "server", "create-session").forSending());
		this.appSessionGot.acquireUninterruptibly();
		LOG.debug(this.name + " has done of acquireSession.");
		this.appSessionGot = null;
		return this.appSessionId;
	}

	public String getSessionId() {
		return this.appSessionId;
	}
	
	@Override
	protected void onCloseException(Exception e) {
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
