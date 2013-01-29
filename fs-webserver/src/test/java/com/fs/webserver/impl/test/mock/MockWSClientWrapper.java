/**
 *  Dec 12, 2012
 */
package com.fs.webserver.impl.test.mock;

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;
import com.fs.commons.api.struct.Path;
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

	protected String wsId;

	protected Semaphore serverIsReady;

	public MockWSClientWrapper(WSClient t) {
		super(t);

		this.addHandler(Path.valueOf("server-is-ready"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockWSClientWrapper.this.serverIsReady(sc);
			}
		});
		this.addHandler(Path.valueOf("echo-from-server"), true, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockWSClientWrapper.this.echoFromServer(sc);

			}
		});

	}

	@Override
	public WSClientWrapper connect() {
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
	protected void serverIsReady(MessageContext sc) {
		MockMessageWrapper mw = MockMessageWrapper.valueOf(sc.getRequest());
		this.wsId = mw.getWsId(true);
		if (this.serverIsReady != null) {// first

			this.serverIsReady.release();
			return;

		}
	}

	public void echo(String text) {
		MockMessageWrapper mw = MockMessageWrapper.valueOf("echo", text);
		this.syncSendMessage(mw);
	}

	public String getWsId(boolean force) {
		if (this.wsId == null && force) {
			throw new FsException("wsId not got from server,please see connect()");
		}
		return wsId;
	}

}
