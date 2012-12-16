/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.impl.gobject;

import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.support.GridedObjectSupport;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class WebSoketGoImpl extends GridedObjectSupport implements WebSocketGoI {

	protected WebSocketI target;

	/**
	 * @param ws
	 */
	public WebSoketGoImpl(WebSocketI ws) {
		this.target = ws;
	}

	/*
	 *Dec 16, 2012
	 */
	@Override
	public void sendMessage(String msg) {
		// 
		this.target.sendMessage(msg);
	}

}
