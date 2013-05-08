/**
 *  Dec 12, 2012
 */
package com.fs.gridservice.commons.api.mock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClient;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.gobject.EndPointGoI;

/**
 * @author wuzhen
 *         <p>
 *         TODO js test:
 *         <p>
 *         http://webtide.intalio.com/2011/08/websocket-example-server-client-
 *         and-loadtest/
 */
public class MockClientWrapper extends BClient {
	private static final Logger LOG = LoggerFactory.getLogger(MockClientWrapper.class);

	public static final String AUTH_AT_CONNECT = "authAtConnect";

	public static final String CREDENTIAL = "credential";

	protected Semaphore serverIsReady;

	protected Semaphore authed;

	protected String clientId;

	protected String terminalId;

	protected String accountId;

	protected String sessionId;// app level session.

	public MockClientWrapper(AClientI t, PropertiesI pts) {
		super(t, pts);

		this.target.addHandler(EndPointGoI.P_SERVER_IS_READY, new HandlerI<MessageContext>() {

			@Override
			public void handle(MessageContext sc) {
				MockClientWrapper.this.onServerIsReady(sc);
			}
		});
		this.target.addHandler(Path.valueOf("/terminal/auth/success"), new HandlerI<MessageContext>() {

			@Override
			public void handle(MessageContext sc) {
				MockClientWrapper.this.onAuthSuccess(sc);
			}
		});

	}

	@Override
	public MockClientWrapper connect() {
		super.connect();

		try {

			//
			this.serverIsReady = new Semaphore(0);
			MessageI msg = new MessageSupport(EndPointGoI.P_CLIENT_IS_READY.toString());// cause
																						// serverIsReady
			super.sendMessage(msg);

			if (!this.serverIsReady.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("timeout to wait the server is ready");

			}//

		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
		boolean auth = this.properties.getPropertyAsBoolean(MockClientWrapper.AUTH_AT_CONNECT, false);
		if (auth) {
			PropertiesI<Object> cred = (PropertiesI<Object>) this.properties.getProperty(
					MockClientWrapper.CREDENTIAL, true);
			this.auth(cred);
		}
		return this;

	}

	private void onServerIsReady(MessageContext sc) {

		MessageI msg = sc.getRequest();//
		this.terminalId = msg.getString("terminalId", true);
		this.clientId = msg.getString("clientId", true);

		this.serverIsReady.release();
	}

	public MockClientWrapper auth(PropertiesI<Object> cre) {

		if (this.clientId == null) {
			throw new FsException("not ready yet");
		}
		this.authed = new Semaphore(0);

		MessageI msg = new MessageSupport() {
		};
		msg.setHeader(MessageI.HK_PATH, "/terminal/auth");
		msg.setPayloads(cre);//
		this.sendMessage(msg);
		try {
			if (!this.authed.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("time out to wait authed.");
			}
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
		return this;
	}

	private void onAuthSuccess(MessageContext sc) {
		MessageI msg = sc.getRequest();//
		Path path = msg.getPath();

		String sid = (String) msg.getPayload("sessionId", true);
		String accId = msg.getString("accountId", true);
		this.sessionId = sid;
		this.accountId = accId;//
		this.authed.release();

	}

	@Override
	public void sendMessage(MessageI msg) {

		if (this.terminalId == null) {
			throw new FsException("no terminalId");
		}
		msg.setHeader(MessageI.HK_RESPONSE_ADDRESS, "tid://" + this.terminalId);
		super.sendMessage(msg);
	}

	public String getTerminalId() {
		return terminalId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

}
