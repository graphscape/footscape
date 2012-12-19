/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.AuthProviderI;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;
import com.fs.gridservice.commons.impl.support.TerminalMsgReseiveEventHandler;

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
	public void handleAuth(TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE,
			RequestI req) {
		String accId = (String) reqE.getMessage().getPayload("accountId", true);
		String pass = (String) reqE.getMessage().getPayload("password", true);

		boolean ok = this.authProvider.auth(accId, pass);
		SessionGd s = new SessionGd();
		s.setProperty(SessionGd.ACCID, accId);
		s.setProperty(SessionGd.TERMIANAlID, reqE.getTerminalId());

		String sid = this.sessionManager.createSession(s);

		if (ok) {
			MessageI msg = this.newResponseSuccessMessage(reqE);
			msg.setPayload("sessionId", sid);
			this.sendMessage(msg);
		} else {

		}
	}
}
