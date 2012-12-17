/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.impl.gobject;

import org.json.simple.JSONValue;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.expector.api.gobject.WebSocketGoI;
import com.fs.expector.api.support.GridedObjectSupport;
import com.fs.websocket.api.WebSocketI;

/**
 * @author wu
 * 
 */
public class WebSoketGoImpl extends GridedObjectSupport implements WebSocketGoI {

	protected WebSocketI target;

	protected CodecI messageCodec;

	/**
	 * @param ws
	 */
	public WebSoketGoImpl(WebSocketI ws, CodecI messageCodec) {
		this.target = ws;
		this.messageCodec = messageCodec;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void sendTextMessage(String msg) {
		//
		MessageI ms = new MessageSupport() {
		};
		ms.setPayload(msg);//
		this.sendMessage(ms);

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void sendMessage(MessageI msg) {
		JSONValue js = (JSONValue) this.messageCodec.encode(msg);
		String value = JSONValue.toJSONString(js);
		
		this.target.sendMessage(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.expector.api.gobject.WebSocketGoI#sendReady()
	 */
	@Override
	public void sendReady() {
		MessageI msg = new MessageSupport();
		msg.setHeader("path","/control/status/serverIsReady");
		this.sendMessage(msg);
		
	}

}
