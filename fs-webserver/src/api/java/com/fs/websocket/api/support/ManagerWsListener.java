/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.websocket.api.support;

import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsFactoryI;
import com.fs.websocket.api.WsManagerI;

/**
 * @author wu
 * 
 */
public class ManagerWsListener extends AbstractWsListener {

	protected WsFactoryI factory;

	protected String name;

	protected WsManagerI manager;

	public ManagerWsListener(WsFactoryI wf, String manager) {
		this.factory = wf;
		this.name = manager;
	}

	public void start() {
		this.manager = this.factory.getManager(this.name, true);
		this.manager.addListener(this);
		if (LOG.isDebugEnabled()) {
			LOG.debug("started manager ws listener:" + this.name);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		super.onMessage(ws, ms);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		super.onException(ws, t);

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		super.onConnect(ws);
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		super.onClose(ws, statusCode, reason);
	}

}
