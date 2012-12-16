/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl.handler;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.wrapper.WsMsgReceiveEW;
import com.fs.expector.api.wrapper.WsMsgSendEW;

/**
 * @author wu
 * 
 */
public class HandshakeHandler extends WsMsgReseiveEventHandler {

	/*
	 * Dec 16, 2012
	 */
	@Handle("binding")
	public void handleBinding(WsMsgReceiveEW reqE, WsMsgSendEW resE, RequestI req) {
		//
		MessageI msg = reqE.getMessage();
		String sid = (String) msg.getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String wsId = reqE.getWebSocketId();
		this.sessionManager.bindingWebSocket(sid, wsId);
	}

}
