/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.AuthProviderI;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;

/**
 * @author wuzhen
 * 
 */
public class TerminalAuthHandler extends TerminalMsgReseiveEventHandler {

	protected AuthProviderI authProvider;

	@Override
	public void active(ActiveContext ac) {
		// TODO Auto-generated method stub
		super.active(ac);

	}

	@Override
	protected void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();
		this.authProvider = this.facade.getAuthProvider();
	}

	@Handle("auth")
	public void handleAuth(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, RequestI req) {
		String accId = (String) reqE.getMessage().getPayload("accountId", true);
		String pass = (String) reqE.getMessage().getPayload("password", true);

		boolean ok = this.authProvider.auth(accId, pass);

		if (ok) {
			String tid = reqE.getTerminalId();
			TerminalGd tg = this.terminalManager.getTerminal(tid);
			String cid = tg.getClientId(true);//terminal must be bind to client.
			// create a session,
			SessionGd s = new SessionGd();
			s.setProperty(SessionGd.ACCID, accId);
			s.setProperty(SessionGd.CLIENTID, cid);// binding tid;
			String sid = this.sessionManager.createSession(s);
			// binding session with tid:
			this.binding(reqE, tid, s);
		} else {

		}
	}

	@Handle("binding")
	public void handleBinding(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, RequestI req) {
		String sid = reqE.getMessage().getString("sessionId");
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {
			throw new FsException("todo error process");
		}
		String tid = reqE.getTerminalId();
		this.binding(reqE, tid, s);
	}

	protected void binding(TerminalMsgReceiveEW reqE, String tid, SessionGd session) {
		String sid = session.getId();
		this.terminalManager.bindingSession(tid, sid);
		MessageI msg = this.newResponseSuccessMessage(reqE);
		msg.setPayload("sessionId", sid);
		this.sendMessage(msg);

	}
}
