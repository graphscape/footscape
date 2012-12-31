/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.presence;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.Handle;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.presence.PresenceManagerI;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;

/**
 * @author wuzhen
 * 
 */
public class PresenceEventHandler extends TerminalMsgReseiveEventHandler {

	protected PresenceManagerI presenceManager;

	protected TerminalManagerI terminalManager;

	/*
	 * Dec 16, 2012
	 */
	@Handle("leave")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleJoin(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		//
		MessageI msg = reqE.getMessage();
		String sid = (String) msg.getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String tId = reqE.getTerminalId();

		String accId = s.getAccountId();

		this.presenceManager.leave(accId, tId);

	}

}
