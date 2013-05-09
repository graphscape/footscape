/**
 *  Dec 12, 2012
 */
package com.fs.webcomet.impl.test.mock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClient;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockCometBClient extends BClient {
	private static final Logger LOG = LoggerFactory.getLogger(MockCometBClient.class);

	protected String wsId;

	protected Semaphore serverIsReady;

	protected BlockingQueue<MessageI> pushFromServerQueue;


	public MockCometBClient(AClientI t, PropertiesI pts) {
		super(t, pts);
		this.pushFromServerQueue = new LinkedBlockingQueue<MessageI>();
		this.addHandler(Path.valueOf("server-is-ready"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockCometBClient.this.serverIsReady(sc);
			}
		});
		this.addHandler(Path.valueOf("echo-from-server"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockCometBClient.this.echoFromServer(sc);

			}
		});
		this.addHandler(Path.valueOf("push-from-server"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockCometBClient.this.pushFromServer(sc);

			}
		});

	}

	public BlockingQueue<MessageI> getPushFromServerQueue() {
		return pushFromServerQueue;
	}
	
	@Override
	public BClient connect() {
		super.connect();

		MockMessageWrapper mw = MockMessageWrapper.valueOf("client-is-ready", null);
		this.syncSendMessage(mw);

		return this;
	}

	/**
	 * @param sc
	 */
	protected void echoFromServer(MessageContext sc) {
		LOG.info("echo-from-server:" + sc.getRequest());
	}

	/**
	 * @param sc
	 */
	protected void pushFromServer(MessageContext sc) {
		LOG.info("push-from-server:" + sc.getRequest());
		try {
			this.pushFromServerQueue.put(sc.getRequest());
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	/**
	 * @param sc
	 */
	protected void serverIsReady(MessageContext sc) {
		MockMessageWrapper mw = MockMessageWrapper.valueOf(sc.getRequest());
		this.wsId = mw.getWsId(true);
		if (this.serverIsReady != null) {// first

			this.serverIsReady.release();
			return;

		}
	}

	public String echo(String text) {
		MockMessageWrapper mw = MockMessageWrapper.valueOf("echo", text);
		MessageI msg = this.syncSendMessage(mw);
		MockMessageWrapper rtm = MockMessageWrapper.valueOf(msg);
		String rt = rtm.getText();
		return rt;
	}

	public String getWsId(boolean force) {
		if (this.wsId == null && force) {
			throw new FsException("wsId not got from server,please see connect()");
		}
		return wsId;
	}

}
