/**
 *  Dec 12, 2012
 */
package com.fs.gridservice.commons.impl.mock;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageHandlerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.PropertiesI;
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
public class MockClientImpl extends MockClient implements WebSocketListener {
	private static final Logger LOG = LoggerFactory.getLogger(MockClientImpl.class);

	protected URI uri;

	protected WebSocketClient client;

	protected BlockingQueue<MessageI> messageReceived;

	// protected WebSocketConnection connection;

	protected Session session;// ws session not app level.

	protected Semaphore connected;

	protected Semaphore serverIsReady;

	protected Semaphore authed;

	protected Semaphore closed;

	protected String clientId;

	protected String terminalId;

	protected String accountId;

	protected String sessionId;// app level session.

	protected CodecI messageCodec;

	protected MessageServiceI engine;

	protected ExecutorService executor;

	protected String name;

	public MockClientImpl(String name, ContainerI c, URI uri) {
		this.uri = uri;
		this.name = name;

		this.executor = Executors.newFixedThreadPool(1);// TODO
		this.engine = c.find(MessageServiceI.FactoryI.class, true).create("mock-client-" + name);

		this.messageReceived = new LinkedBlockingQueue<MessageI>();
		this.messageCodec = c.find(CodecI.FactoryI.class, true).getCodec(MessageI.class);

		this.client = new WebSocketClient();
		this.getDispatcher().addHandler(null, WebSocketGoI.P_SERVER_IS_READY, new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockClientImpl.this.onServerIsReady(sc);
			}
		});
		this.getDispatcher().addHandler(null, Path.valueOf("/terminal/auth/success"), new MessageHandlerI() {

			@Override
			public void handle(MessageContext sc) {
				MockClientImpl.this.onAuthSuccess(sc);
			}
		});

	}

	@Override
	public MockClient connect() {
		this.connected = new Semaphore(0);// note both the onConnect release
		// this and serverisReady release
		// this.
		this.executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				//
				MockClientImpl.this.run();
				return "stoped";
			}
		});

		try {
			this.client.start();
			Future<Session> sf = this.client.connect(this, this.uri);
			this.session = sf.get(10, TimeUnit.SECONDS);
			// TODO remove?
			if (!this.connected.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("timeout to wait the connection");
			}
			//
			this.serverIsReady = new Semaphore(0);
			MessageI msg = new MessageSupport(WebSocketGoI.P_CLIENT_IS_READY.toString());// cause
																							// serverIsReady
			this.sendMessageDirect(msg);

			if (!this.serverIsReady.tryAcquire(10, TimeUnit.SECONDS)) {
				throw new FsException("timeout to wait the server is ready");
				
			}//

		} catch (Exception e) {
			throw FsException.toRtE(e);
		}

		return this;

	}

	private void onServerIsReady(MessageContext sc) {

		MessageI msg = sc.getRequest();//
		this.terminalId = msg.getString("terminalId", true);
		this.clientId = msg.getString("clientId", true);

		this.serverIsReady.release();
	}

	@Override
	public MockClient auth(PropertiesI<Object> cre) {

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
			this.authed.acquire();
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
		this.sendMessageDirect(msg);
	}

	private void sendMessageDirect(MessageI msg) {
		if (this.session == null) {
			throw new FsException("not connected");
		}
		try {
			JSONArray jsm = (JSONArray) this.messageCodec.encode(msg);//
			String code = jsm.toJSONString();
			this.session.getRemote().sendString(code);
			// this.connection.write(code);
		} catch (Exception e) {
			throw FsException.toRtE(e);
		}
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
		if (this.closed != null) {
			this.closed.release();
		} else {
			LOG.warn("web socket client closed unexpected,may from server?");
		}
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
		// this.connection = connection;

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

	@Override
	public String getTerminalId() {
		return terminalId;
	}

	@Override
	public String getSessionId() {
		return this.sessionId;
	}

	@Override
	public void close() {
		try {
			this.closed = new Semaphore(0);
			this.session.close();
			// this.connection.close();
			// this.connection = null;
			this.closed.acquireUninterruptibly();// TODO allow timeout.
			this.client.stop();
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	public void eachLoop(MessageI msg) throws Exception {
		String path = msg.getHeader(MessageI.HK_PATH);
		if (path == null) {
			throw new FsException("msg has no path,msg:" + msg + "");
		}
		Path v = Path.valueOf(path);
		ResponseI res = this.engine.service(msg);
		if (res.getErrorInfos().hasError()) {
			LOG.error("", res);
		}
	}

	public void run() {
		try {
			this.runInternal();
		} catch (Throwable t) {
			LOG.error("exit loop with exception", t);
		}
	}

	public void runInternal() throws Exception {

		while (true) {
			MessageI msg = this.messageReceived.take();
			this.eachLoop(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.commons.api.mock.MockClient#getDispatcher()
	 */
	@Override
	public DispatcherI<MessageContext> getDispatcher() {
		return this.engine.getDispatcher();
	}
}
