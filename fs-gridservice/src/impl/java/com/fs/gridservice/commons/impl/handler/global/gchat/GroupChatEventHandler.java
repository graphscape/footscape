/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.gchat;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gchat.ChatGroupI;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatMessageGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 * 
 */
public class GroupChatEventHandler extends TerminalMsgReseiveEventHandler {

	protected ChatGroupManagerI chatGroupManager;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.chatGroupManager = this.facade
				.getEntityManager(ChatGroupManagerI.class);
	}

	/*
	 * Dec 16, 2012
	 */
	@Handle("join")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleJoin(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE,
			RequestI req) {
		//
		String tid = reqE.getTerminalId();
		TerminalGd t = this.terminalManager.getTerminal(tid, true);
		String sid = t.getSessionId(true);
		SessionGd s = this.sessionManager.getSession(sid);

		if (s == null) {// TODO event,code?
			throw new FsException("TODO,session not found by tid:" + tid);
		}

		MessageI msg = reqE.getMessage();
		String gId = (String) msg.getPayload("groupId", true);

		ChatGroupI cr = this.chatGroupManager.getChatGroup(gId);
		if (cr == null) {
			PropertiesI<Object> pts = new MapProperties<Object>();// TODO
			cr = this.chatGroupManager.createChatRoom(pts);
		}

		this.sendResponseSuccessMessage(reqE);//

	}

	@Handle("message")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleBinding(TerminalMsgReceiveEW reqE,
			TerminalMsgSendEW resE, RequestI req) {
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
		ChatGroupI cr = this.chatGroupManager.getChatRoom(crId, true);

		ChatMessageGd cm = new ChatMessageGd();// TODO

		cr.dispatch(cm);
		// TODO move to uper class.
		MessageI m2 = new MessageSupport();
		m2.setHeader("path", "/chat/dispatch/success");//
		// TODO get the queue of the member that contains the web socket
		String mid = this.facade.getLocalMember().getId();

		DgQueueI<EventGd> mqueue = this.facade.getMemberEventQueue(mid);

		TerminalMsgSendEW ok = TerminalMsgSendEW
				.valueOf("/wsmsg/send", tId, m2);

		mqueue.offer(ok.getTarget());

	}
}
