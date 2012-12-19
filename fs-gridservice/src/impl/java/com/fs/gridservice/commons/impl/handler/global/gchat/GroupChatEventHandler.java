/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.gchat;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.gchat.data.ChatGroupGd;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.support.TerminalMsgReseiveEventHandler;

/**
 * @author wu
 * 
 */
public class GroupChatEventHandler extends TerminalMsgReseiveEventHandler {

	protected ChatGroupManagerI chatGroupManager;

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.chatGroupManager = this.facade.getEntityManager(ChatGroupManagerI.class);
	}

	/*
	 * Dec 16, 2012
	 */
	@Handle("join")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleJoin(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, RequestI req) {
		//
		String tid = reqE.getTerminalId();
		TerminalGd t = this.terminalManager.getTerminal(tid, true);
		String sid = t.getSessionId(true);
		SessionGd s = this.sessionManager.getSession(sid);

		if (s == null) {// TODO event,code?
			throw new FsException("TODO,session not found by tid:" + tid);
		}

		MessageI msg = reqE.getMessage();
		String gId = msg.getHeader("groupId", true);

		// create group
		ChatGroupGd cr = this.chatGroupManager.getChatGroup(gId);
		if (cr == null) {
			PropertiesI<Object> pts = new MapProperties<Object>();// TODO
			cr = this.chatGroupManager.createChatRoom(gId, pts);
		}

		// add participant
		ParticipantGd rt = new ParticipantGd();
		rt.setProperty(ParticipantGd.PK_GROUPID, gId);
		rt.setProperty(ParticipantGd.PK_ACCID, s.getAccountId());
		rt.setProperty(ParticipantGd.PK_ROLE, "TODO");//
		rt.setProperty(ParticipantGd.PK_TERMINALID, tid);//

		this.chatGroupManager.addParticipant(rt);

		this.sendResponseSuccessMessage(reqE);//

	}

	@Handle("message")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleMessage(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, RequestI req) {
		//
		MessageI msg = reqE.getMessage();

		String tid = reqE.getTerminalId();
		TerminalGd td = this.terminalManager.getTerminal(tid);
		String sid = td.getSessionId();

		if (sid == null) {
			throw new FsException("TODO,terminal" + tid + " not auth");
		}

		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String tId = reqE.getTerminalId();
		if (tId == null) {
			throw new FsException("TODO");
		}

		String crId = msg.getHeader("groupId", true);
		String format = msg.getHeader("format", true);
		if (!format.equals("message")) {
			throw new FsException("format:" + format + " not support");
		}

		MessageI msg2 = (MessageI) msg.getPayload("message");
		this.broadcast(crId, msg2);//

		this.sendResponseSuccessMessage(reqE);

	}

	public void broadcast(String gid, MessageI msg) {
		List<ParticipantGd> pL = this.chatGroupManager.getParticipantList(gid);
		for (ParticipantGd p : pL) {
			MessageI msg0 = new MessageSupport();
			msg0.setHeader("path", "/gchat/message");//
			msg0.setPayload("message", msg);// NOTE nested message
			String tid = p.getTerminalId();//
			this.terminalManager.sendMessage(tid, msg0);
		}
	}
}
