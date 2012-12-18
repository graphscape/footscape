/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.handler.chat;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.chat.ChatRoomI;
import com.fs.gridservice.commons.api.chat.ChatRoomManagerI;
import com.fs.gridservice.commons.api.chat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.WsMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;
import com.fs.gridservice.commons.impl.support.WsMsgReseiveEventHandler;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wuzhen
 * 
 */
public class ChatMessageEventHandler extends WsMsgReseiveEventHandler {

	protected ChatRoomManagerI chatRoomManager;

	/*
	 * Dec 16, 2012
	 */
	@Handle("dispatch")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleBinding(WsMsgReceiveEW reqE, WsMsgSendEW resE,
			RequestI req) {
		//
		MessageI msg = reqE.getMessage();
		String sid = (String) msg.getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String wsoId = reqE.getWebSocketId();
		if (wsoId == null) {
			throw new FsException("TODO");
		}

		String crId = msg.getHeader("chatRoomId", true);
		ChatRoomI cr = this.chatRoomManager.getChatRoom(crId, true);

		ChatMessageGd cm = new ChatMessageGd();// TODO

		cr.dispatch(cm);
		// TODO move to uper class.
		MessageI m2 = new MessageSupport();
		m2.setHeader("path", "/chat/dispatch/success");//
		// TODO get the queue of the member that contains the web socket
		String mid = this.facade.getLocalMember().getId();

		DgQueueI<EventGd> mqueue = this.facade.getMemberEventQueue(mid);

		WsMsgSendEW ok = WsMsgSendEW.valueOf("/wsmsg/send", wsoId, m2);

		mqueue.offer(ok.getTarget());

	}

}
