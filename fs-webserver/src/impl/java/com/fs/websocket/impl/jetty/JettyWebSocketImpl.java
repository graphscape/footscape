/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.websocket.impl.jetty;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.io.WebSocketBlockingConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.lang.FsException;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsListenerI;
import com.fs.websocket.api.support.CollectionWsListener;

/**
 * @author wu
 * 
 */
public class JettyWebSocketImpl extends CollectionWsListener implements WebSocketI, WebSocketListener {

	private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketImpl.class);

	protected Session connection;

	protected WebSocketBlockingConnection blocking;

	protected String id;

	public JettyWebSocketImpl(String id) {
		this.id = id;

	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public String getId() {
		//
		return this.id;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void sendMessage(String msg) {
		try {
			this.blocking.write(msg);
		} catch (IOException e) {
			throw new FsException(e);
		}
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		this.connection = null;
		LOG.info("onWebSocketClose,statusCode:" + statusCode + ",reason:" + reason);
		super.onClose(this, statusCode, reason);
	}

	@Override
	public void onWebSocketConnect(Session connection) {
		LOG.info("onWebSocketConnect");
		this.connection = connection;
		this.blocking = new WebSocketBlockingConnection(this.connection);
		super.onConnect(this);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketError(Throwable error) {
		LOG.error("onWebSocketException,error:" + error);
		super.onException(this, error);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		// TODO
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onWebSocketText(String message) {
		//
		super.onMessage(this, message);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void addListener(WsListenerI ln) {
		//
		super.add(ln);
	}
}
