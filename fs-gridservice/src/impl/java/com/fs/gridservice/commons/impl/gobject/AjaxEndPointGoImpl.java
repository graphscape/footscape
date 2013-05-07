/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.impl.gobject;

import com.fs.commons.api.codec.CodecI;
import com.fs.gridservice.commons.api.support.EndPointGoSupport;
import com.fs.gridservice.commons.impl.terminal.ajax.AjaxSession;

/**
 * @author wu
 * 
 */
public class AjaxEndPointGoImpl extends EndPointGoSupport<AjaxSession> {

	/**
	 * @param ws
	 */
	public AjaxEndPointGoImpl(AjaxSession ws, CodecI messageCodec) {
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
