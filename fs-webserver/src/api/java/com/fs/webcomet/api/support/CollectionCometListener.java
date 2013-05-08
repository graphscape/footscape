/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.webcomet.api.support;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.context.support.ContextSupport;
import com.fs.commons.api.util.StringUtil;
import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;

/**
 * @author wu
 * 
 */
public class CollectionCometListener extends ContextSupport implements CometListenerI {

	protected List<CometListenerI> listeners;

	public CollectionCometListener() {
		this.listeners = new ArrayList<CometListenerI>();
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, String msg) {
		//
		for (CometListenerI l : this.listeners) {
			l.onMessage(ws, msg);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onException(ws, t);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onConnect(ws);
		}
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		//
		//
		for (CometListenerI l : this.listeners) {
			l.onClose(ws, statusCode, reason);
		}
	}

	/**
	 * Dec 12, 2012
	 */
	public void add(CometListenerI ln) {
		this.listeners.add(ln);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.websocket.api.WsListenerI#onMessage(com.fs.websocket.api.WebSocketI
	 * , java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}
}
