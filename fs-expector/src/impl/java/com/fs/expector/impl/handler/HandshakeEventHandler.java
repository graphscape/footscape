/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.impl.handler;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.api.data.EventGd;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.wrapper.WsMsgReceiveEW;
import com.fs.expector.api.wrapper.WsMsgSendEW;

/**
 * @author wu
 * 
 */
public class HandshakeEventHandler extends WsMsgReseiveEventHandler {

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
		if (wsId == null) {
			throw new FsException("TODO");
		}
		this.sessionManager.bindingWebSocket(sid, wsId);

		MessageI m2 = new MessageSupport();

		// TODO get the queue of the member that contains the web socket
		String mid = this.facade.getLocalMember().getId();

		DgQueueI<EventGd> mqueue = this.facade.getMemberEventQueue(mid);

		WsMsgSendEW ok = WsMsgSendEW.valueOf("/wsmessage/send", m2);

		mqueue.offer(ok.getTarget());

	}

}
