/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.websocket.api.support;

import com.fs.websocket.api.WebSocketI;
import com.fs.websocket.api.WsListenerI;

/**
 * @author wu
 * 
 */
public class InstanceWsListener implements WsListenerI {

	protected WebSocketI socket;

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(WebSocketI ws, String ms) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(WebSocketI ws, Throwable t) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(WebSocketI ws) {
		//
		this.socket = ws;
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(WebSocketI ws, int statusCode, String reason) {
		//

	}

}
