/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.websocket.api.mock.WSClient;
import com.fs.websocket.api.mock.WSClientWrapper;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockWSClientWrapper extends WSClientWrapper {
	private static final Logger LOG = LoggerFactory.getLogger(MockWSClientWrapper.class);

	protected BlockingQueue<MockMessageWrapper> messageReceived;

	protected Semaphore appSessionGot;

	protected String appSessionId;

	protected ExecutorService executor;

	protected Semaphore serverIsReady;

	public MockWSClientWrapper(WSClient t) {
		super(t);
		this.executor = Executors.newCachedThreadPool();
		this.messageReceived = new LinkedBlockingQueue<MockMessageWrapper>();
	}

	public MockMessageWrapper nextMessage(long timeout) {
		try {
			return this.messageReceived.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new FsException(e);//
		}
	}

	@Override
	protected void onMessage(MessageContext mc) {
		MessageI msg = mc.getRequest();
		MockMessageWrapper ms = MockMessageWrapper.valueOf(msg);

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

				return MockWSClientWrapper.this.doCreateSession();
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
		this.sendMessage(MockMessageWrapper.valueOf("client", "server", "create-session"));
		this.appSessionGot.acquireUninterruptibly();
		LOG.debug(this.name + " has done of acquireSession.");
		this.appSessionGot = null;
		return this.appSessionId;
	}

	public String getSessionId() {
		return this.appSessionId;
	}

}
