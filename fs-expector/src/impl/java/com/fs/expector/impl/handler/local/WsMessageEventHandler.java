/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.expector.impl.handler.local;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.api.wrapper.WsMsgSendEW;
import com.fs.expector.impl.handler.WsMsgReseiveEventHandler;

/**
 * @author wu
 * 
 */
public class WsMessageEventHandler extends WsMsgReseiveEventHandler {

	/*
	 * Dec 16, 2012
	 */
	@Handle("send")
	public void handleBinding(WsMsgSendEW reqE, RequestI req) {
		//
		MessageI msg = reqE.getMessage();

		String wsId = reqE.getWebSocketId();

		MessageI m2 = new MessageSupport();

	}
}