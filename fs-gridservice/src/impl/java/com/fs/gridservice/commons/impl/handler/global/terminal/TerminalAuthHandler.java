/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.AuthProviderI;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.terminal.data.TerminalGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.api.wrapper.internal.InternalMsgEW;

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
	public void handleAuth(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		PropertiesI<Object> cre = reqE.getMessage().getPayloads();

		PropertiesI<Object> ok = this.authProvider.auth(cre);

		if (ok != null) {
			String tid = reqE.getTerminalId();
			TerminalGd tg = this.terminalManager.getTerminal(tid);
			String cid = tg.getClientId(true);// terminal must be bind to
												// client.
			// create a session,
			SessionGd s = new SessionGd();

			String accId = (String) ok.getProperty(SessionGd.ACCID, true);
			s.setProperties(ok);
			s.setProperty(SessionGd.CLIENTID, cid);// binding tid;
			String sid = this.sessionManager.createSession(s);
			// binding session with tid:
			this.binding(ok, reqE, tid, s);
		} else {

		}
	}

	@Handle("binding")
	// directly binding session with terminal
	public void handleBinding(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		String sid = reqE.getMessage().getString("sessionId", true);//
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {
			throw new FsException("todo error process");
		}
		String tid = reqE.getTerminalId();
		this.binding(null, reqE, tid, s);
	}

	protected void binding(PropertiesI<Object> res, TerminalMsgReceiveEW reqE, String tid, SessionGd session) {
		String sid = session.getId();
		String aid = session.getAccountId();
		String cid = reqE.getClientId();
		this.terminalManager.bindingSession(tid, sid);
		this.clientManager.bindingSession(cid, sid);
		MessageI msg = this.newResponseSuccessMessage(reqE);
		if (res != null) {
			msg.setPayloads(res);
		}
		msg.setPayload("sessionId", sid);
		msg.setPayload("accountId", aid);
		this.sendMessage(msg);
		InternalMsgEW sb = InternalMsgEW.valueOf("/internal/after-session-binding", msg);
		this.raiseEvent(sb);

	}

}
