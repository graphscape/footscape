/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tigase.jaxmpp.core.client.Connector;
import tigase.jaxmpp.core.client.Connector.ConnectorEvent;
import tigase.jaxmpp.core.client.Connector.State;
import tigase.jaxmpp.core.client.DefaultSessionObject;
import tigase.jaxmpp.core.client.JID;
import tigase.jaxmpp.core.client.SessionObject;
import tigase.jaxmpp.core.client.connector.AbstractBoshConnector;
import tigase.jaxmpp.core.client.exceptions.JaxmppException;
import tigase.jaxmpp.core.client.observer.DefaultObservable;
import tigase.jaxmpp.core.client.observer.Listener;
import tigase.jaxmpp.core.client.observer.Observable;
import tigase.jaxmpp.core.client.xml.Element;
import tigase.jaxmpp.core.client.xml.XMLException;
import tigase.jaxmpp.j2se.connectors.bosh.BoshConnector;
import tigase.jaxmpp.j2se.connectors.socket.SocketConnector;

import com.fs.commons.api.lang.FsException;
import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.api.XmppSessionI;

;
/**
 * @author wu have reference from:Jaxmpp.java from tigase.
 */
public class XmppSession implements XmppSessionI {

	private static final Logger LOG = LoggerFactory
			.getLogger(XmppSession.class);

	protected Connector connector;

	protected final Observable observable = new DefaultObservable(null);

	protected SessionObject sessionObject;

	protected Map<String, BlockingQueue<StanzaI>> resultMonitor = new HashMap<String, BlockingQueue<StanzaI>>();

	protected BlockingQueue<Boolean> connected;

	protected long connectionTimeoutMS = 1000 * 10;

	protected String domain;

	public XmppSession(String host, String domain) {
		this.domain = domain;
		sessionObject = new DefaultSessionObject();
		this.sessionObject.setUserProperty("serverName", domain);
		this.sessionObject.setUserProperty("userJid",
				JID.jidInstance("c2s@" + domain));// NOTE
		if (false) {
			this.sessionObject.setUserProperty("socket#ServerHost", host);
			connector = new SocketConnector(observable, sessionObject);
		} else {
			this.sessionObject.setUserProperty(
					AbstractBoshConnector.BOSH_SERVICE_URL_KEY, "http://"
							+ host + ":5280/http-bind");
			connector = new BoshConnector(observable, sessionObject);
		}
		Listener<ConnectorEvent> srl = new Listener<ConnectorEvent>() {

			@Override
			public void handleEvent(ConnectorEvent be) throws JaxmppException {
				if (be.getStanza() != null)
					onStanzaReceived(be.getStanza());
			}
		};
		connector.addListener(Connector.StanzaReceived, srl);
		connector.addListener(Connector.StateChanged,
				new Listener<ConnectorEvent>() {

					@Override
					public void handleEvent(ConnectorEvent be)
							throws JaxmppException {
						onStateChanged(be);

					}
				});

	}

	public Future<XmppSessionI> start() {
		this.connected = new LinkedBlockingQueue<Boolean>();
		try {
			this.connector.start();
			// this.connector.keepalive();
		} catch (XMLException e) {
			throw new FsException(e);
		} catch (JaxmppException e) {
			throw new FsException(e);

		}
		final long timeout = this.connectionTimeoutMS;
		Future<XmppSessionI> rt = FutureUtil
				.runInFuture(new Callable<XmppSessionI>() {

					@Override
					public XmppSessionI call() throws Exception {
						Boolean connected = XmppSession.this.connected.poll(
								timeout, TimeUnit.MILLISECONDS);
						if (connected == null) {
							throw new FsException("connection timeout(MS):"
									+ timeout + ",sessionobject:"
									+ XmppSession.this.sessionObject);
						}
						if (!connected) {
							throw new FsException(
									"connection failure,session object:"
											+ XmppSession.this.sessionObject);
						}
						return XmppSession.this;
					}
				});

		return rt;
	}

	protected void onStateChanged(ConnectorEvent ce) {
		LOG.info("ConnectorEvent:" + ce);
		State s = this.connector.getState();

		Boolean put = null;
		if (s.equals(State.connected)) {
			if (this.connected != null) {
				put = Boolean.TRUE;
			}
		} else if (s.equals(State.disconnected)) {
			put = Boolean.FALSE;
		} else {
			// ignore other state
			return;
		}

		try {
			this.connected.put(put);
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

	}

	protected void onStanzaReceived(Element stanza) {
		StanzaImpl si = new StanzaImpl(stanza, this);
		String id = si.getId();
		BlockingQueue<StanzaI> req = this.resultMonitor.get(id);
		if (req != null) {
			try {
				req.put(si);
			} catch (InterruptedException e) {
				throw new FsException(e);
			}
			LOG.info("response:" + si + ",");
		} else {
			LOG.info("no request found for response:" + si);
		}

		//
	}

	public StanzaI sendSync(final StanzaImpl si) {
		Future<StanzaI> fs = this.sendAsync(si);
		try {
			return fs.get();
		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			throw new FsException(e.getCause());
		}
	}

	public Future<StanzaI> sendAsync(final StanzaImpl si) {
		LOG.info("send request:" + si);
		BlockingQueue<StanzaI> bl = new LinkedBlockingQueue<StanzaI>();

		this.resultMonitor.put(si.getId(), bl);//
		Element ele = si.element;
		try {
			this.connector.send(ele);
		} catch (XMLException e) {
			throw new FsException(e);
		} catch (JaxmppException e) {
			throw new FsException(e);
		}
		final FutureTask<StanzaI> rt = new FutureTask<StanzaI>(
				new Callable<StanzaI>() {

					@Override
					public StanzaI call() throws Exception {

						return XmppSession.this.getResponseInFuture(si);
					}
				});
		// TODO thread pool.
		new Thread(new Runnable() {

			@Override
			public void run() {
				rt.run();
			}
		}).start();
		// TODO in a thread?

		return rt;

	}

	protected StanzaI getResponseInFuture(StanzaI req) {
		String id = req.getId();
		BlockingQueue<StanzaI> s = this.resultMonitor.get(id);
		// waiting the response
		StanzaI rt;
		try {
			rt = s.take();
		} catch (InterruptedException e) {
			throw new FsException(e);
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmpp.api.XmppConnectionI#close()
	 */
	@Override
	public void close() {
		try {
			this.connector.stop();
		} catch (XMLException e) {
			throw new FsException(e);
		} catch (JaxmppException e) {
			throw new FsException(e);

		}
	}

	@Override
	public StanzaI createStanza(String name) {
		// TODO Auto-generated method stub
		return new StanzaImpl(name, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.xmppclient.api.XmppConnectionI#bind(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public XmppSessionI auth(String username, String password) {
		// Auth
		/**
		 * IqAuthI auth = this.create(IqAuthI.class); auth.setType("set");
		 * auth.setFrom("c2s@" + domain);// NOTE auth.setTo(domain);// NOTE
		 * auth.setUserName(username); auth.setPassword(password); StanzaI auRt
		 * = auth.sendSync(); auRt.assertNoError(); // binding
		 **/
		return this;
	}

	@Override
	public XmppSessionI bind(String res) {
		/**
		 * IqBindI bd = this.create(IqBindI.class); bd.setType("set");
		 * bd.setFrom("c2s@" + domain);// bd.setTo(domain);// NOTE
		 * bd.setResource(res);// StanzaI rt = bd.sendSync();
		 * rt.assertNoError();
		 **/
		return this;
	}

}
