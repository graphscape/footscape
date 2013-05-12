/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.gobject;

import com.fs.commons.api.codec.CodecI;
import com.fs.gridservice.commons.api.support.EndPointGoSupport;
import com.fs.webcomet.api.CometI;

/**
 * @author wu
 * 
 */
public class EndPointGoImpl extends EndPointGoSupport<CometI> {

	/**
	 * @param ws
	 */
	public EndPointGoImpl(CometI ws, CodecI messageCodec) {
		super(ws, messageCodec);
	}

	/*
	 * May 7, 2013
	 */
	@Override
	protected void doSendMessage(String msg) {
		this.target.sendMessage(msg);
	}

	/*
	 * May 12, 2013
	 */
	@Override
	public String getProtocol() {
		//
		return this.target.getProtocol();
	}

}
