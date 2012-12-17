/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.websocket.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsListenerI;

/**
 * @author wu
 * 
 */
public class CollectionWsListener extends ContextSupport implements WsListenerI {

	protected List<WsListenerI> listeners;

	public CollectionWsListener() {
		this.listeners = new ArrayList<WsListenerI>();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String msg) {
		//
		for (WsListenerI l : this.listeners) {
			l.onMessage(ws, msg);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//
		//
		for (WsListenerI l : this.listeners) {
			l.onException(ws, t);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		//
		//
		for (WsListenerI l : this.listeners) {
			l.onConnect(ws);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		//
		//
		for (WsListenerI l : this.listeners) {
			l.onClose(ws, statusCode, reason);
		}
	}

	/**
	 *Dec 12, 2012
	 */
	public void add(WsListenerI ln) {
		this.listeners.add(ln);
	}
}
