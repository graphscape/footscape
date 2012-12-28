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
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;

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
		String gid = msg.getHeader("groupId", true);

		// create group
		ChatGroupGd cr = this.chatGroupManager.getChatGroup(gid);
		if (cr == null) {
			PropertiesI<Object> pts = new MapProperties<Object>();// TODO
			cr = this.chatGroupManager.createChatRoom(gid, pts);
		}

		String aid = s.getAccountId();
		String role = "todo";

		// add participant
		ParticipantGd rt = new ParticipantGd();
		rt.setProperty(ParticipantGd.PK_GROUPID, gid);
		rt.setProperty(ParticipantGd.PK_ACCID, aid);
		rt.setProperty(ParticipantGd.PK_ROLE, role);//
		rt.setProperty(ParticipantGd.PK_TERMINALID, tid);//

		this.chatGroupManager.addParticipant(rt);//
		// join successed

		// send the existing participant as join message to the new joined
		// participant.

		// notify all participant
		String pid = rt.getId();
		// this is a nested message
		this.onJoined(rt);//

		// response
		MessageI res = this.newResponseSuccessMessage(reqE);
		res.setPayload("participantId", pid);//
		this.sendMessage(res);
	}

	private void onJoined(ParticipantGd newJoined) {
		String gid = newJoined.getGroupId();
		List<ParticipantGd> pL = this.chatGroupManager.getParticipantList(gid);
		// notify new joined the current part list,include himself
		for (ParticipantGd p : pL) {
			this.sendJoinMessage(p, newJoined);
		}

		// notify other part the new joined,not include himself
		for (ParticipantGd p : pL) {
			if (p.isIdEquals(newJoined)) {
				continue;//
			}
			this.sendJoinMessage(newJoined, p);//
		}
	}

	private void sendJoinMessage(ParticipantGd joined, ParticipantGd target) {

		String pid = joined.getId();
		String aid = joined.getAccountId();
		String role = joined.getRole();
		String tid = joined.getTerminalId();
		String gid = joined.getGroupId();

		MessageI msg2 = new MessageSupport();
		msg2.setPayload("participantId", pid);
		msg2.setPayload("accountId", aid);
		msg2.setPayload("role", role);
		msg2.setPayload("terminalId", tid);//

		this.sendGroupMessage("/gchat/join", gid, joined.getId(),
				target.getId(), msg2);
	}

	@Handle("exit")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleExit(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE,
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
		String gid = msg.getHeader("groupId", true);

		// create group
		ChatGroupGd cr = this.chatGroupManager.getChatGroup(gid, true);

		ParticipantGd p = this.getParticipantIdByTerminalId(gid, tid, true);//
		String pid = p.getId();
		this.chatGroupManager.removeParticipant(gid, pid);

		// join successed
		// notify all participant
		// this is a nested message
		MessageI msg2 = new MessageSupport();

		msg2.setPayload("participantId", pid);

		PropertiesI<String> hds = new MapProperties<String>();

		this.broadcast("/gchat/exit", gid, p.getId(), msg2);

		// response
		MessageI res = this.newResponseSuccessMessage(reqE);
		this.sendMessage(res);
	}

	@Handle("message")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleMessage(TerminalMsgReceiveEW reqE,
			TerminalMsgSendEW resE, RequestI req) {
		//
		MessageI msg = reqE.getMessage();

		String tid = reqE.getTerminalId();
		TerminalGd td = this.terminalManager.getTerminal(tid);
		String sid = td.getSessionId(true);

		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String tId = reqE.getTerminalId();
		if (tId == null) {
			throw new FsException("TODO");
		}

		String gid = msg.getHeader("groupId", true);
		String format = msg.getHeader("format", true);
		if (!format.equals("message")) {
			throw new FsException("format:" + format + " not support");
		}
		ParticipantGd p = this.getParticipantIdByTerminalId(gid, tid, true);
		String pid = p.getId();
		MessageI msg2 = (MessageI) msg.getPayload("message", true);
		this.broadcast("/gchat/message", gid, pid, msg2);//

		this.sendResponseSuccessMessage(reqE);

	}

	protected ParticipantGd getParticipantIdByTerminalId(String gid,
			String tid, boolean force) {
		List<ParticipantGd> pL = this.chatGroupManager.getParticipantList(gid);
		return this.getParticipantIdByTerminalId(gid, tid, pL, force);

	}

	protected ParticipantGd getParticipantIdByTerminalId(String gid,
			String tid, List<ParticipantGd> pL, boolean force) {

		for (ParticipantGd p : pL) {
			if (p.getTerminalId().equals(tid)) {
				return p;
			}
		}
		if (force) {
			throw new FsException("no participant found for terminal" + tid
					+ " in group:" + gid);
		}
		return null;

	}

	public void broadcast(String path, String gid, String fromPid,
			MessageI payload) {

		List<ParticipantGd> pL = this.chatGroupManager.getParticipantList(gid);

		for (ParticipantGd p : pL) {
			this.sendGroupMessage(path, gid, fromPid, p.getId(), payload);
		}
	}

	public void sendGroupMessage(String path, String gid, String fromPid,
			String toPid, MessageI nested) {

		ParticipantGd p2 = this.chatGroupManager.getParticipantManager()
				.getEntity(toPid);

		MessageI msg0 = new MessageSupport();
		msg0.setHeader(MessageI.HK_PATH, path);//
		msg0.setHeader("groupId", gid);
		msg0.setHeader("participantId", fromPid);

		msg0.setPayload("message", nested);// NOTE nested message
		String tid = p2.getTerminalId();//
		this.terminalManager.sendMessage(tid, msg0);
	}
}
