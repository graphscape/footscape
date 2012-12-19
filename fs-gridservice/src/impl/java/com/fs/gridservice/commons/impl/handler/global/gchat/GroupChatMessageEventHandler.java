/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.gchat;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gchat.ChatGroupI;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wuzhen
 * 
 */
public class GroupChatMessageEventHandler extends TerminalMsgReseiveEventHandler {

	protected ChatGroupManagerI chatRoomManager;

	/*
	 * Dec 16, 2012
	 */
	@Handle("send")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleBinding(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE,
			RequestI req) {
		//
		MessageI msg = reqE.getMessage();
		String sid = (String) msg.getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String tId = reqE.getTerminalId();
		if (tId == null) {
			throw new FsException("TODO");
		}

		String crId = msg.getHeader("groupId", true);
		ChatGroupI cr = this.chatRoomManager.getChatRoom(crId, true);

		ChatMessageGd cm = new ChatMessageGd();// TODO

		cr.dispatch(cm);
		// TODO move to uper class.
		MessageI m2 = new MessageSupport();
		m2.setHeader("path", "/chat/dispatch/success");//
		// TODO get the queue of the member that contains the web socket
		String mid = this.facade.getLocalMember().getId();

		DgQueueI<EventGd> mqueue = this.facade.getMemberEventQueue(mid);

		TerminalMsgSendEW ok = TerminalMsgSendEW.valueOf("/wsmsg/send", tId, m2);

		mqueue.offer(ok.getTarget());

	}

}
