/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.fs.websocket.api.support;

import java.io.Reader;

import com.fs.webcomet.api.CometI;
import com.fs.webcomet.api.CometListenerI;

/**
 * @author wu
 * 
 */
public class InstanceWsListener implements CometListenerI {

	protected CometI socket;

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onMessage(CometI ws, String ms) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		//

	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onConnect(CometI ws) {
		//
		this.socket = ws;
	}

	/*
	 * Dec 12, 2012
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		//

	}

	/* (non-Javadoc)
	 * @see com.fs.websocket.api.WsListenerI#onMessage(com.fs.websocket.api.WebSocketI, java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		// TODO Auto-generated method stub
		
	}

}
