/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.gobject;

import com.fs.commons.api.codec.CodecI;
import com.fs.gridservice.commons.api.support.EndPointGoSupport;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class WebSoketEndPointGoImpl extends EndPointGoSupport<WebSocketI> {

	/**
	 * @param ws
	 */
	public WebSoketEndPointGoImpl(WebSocketI ws, CodecI messageCodec) {
		super(ws, messageCodec);
	}

	/*
	 * May 7, 2013
	 */
	@Override
	protected void doSendMessage(String msg) {
		this.target.sendMessage(msg);
	}

}
