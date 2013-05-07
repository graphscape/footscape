/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.terminal;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
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
	public void handleAuth(ResponseI res, TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		PropertiesI<Object> cre = reqE.getMessage().getPayloads();
		ErrorInfos eis = new ErrorInfos();
		PropertiesI<Object> ok = new MapProperties<Object>();
		this.authProvider.auth(cre,eis,ok);

		res.setPayloads(cre);// for tracking

		if (eis.hasError()) {
			res.getErrorInfos().addAll(eis);
			return;
		}
		String tid = reqE.getTerminalId(true);
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
		this.binding(res, ok, reqE, tid, s);
	}

	@Handle("binding")
	// directly binding session with terminal
	public void handleBinding(ResponseI res, TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		String sid = reqE.getMessage().getString("sessionId", true);//
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {
			throw new FsException("todo error process");
		}
		String tid = reqE.getTerminalId();
		this.binding(res, null, reqE, tid, s);
	}

	protected void binding(ResponseI res, PropertiesI<Object> pts, TerminalMsgReceiveEW reqE, String tid,
			SessionGd session) {
		String sid = session.getId();
		String aid = session.getAccountId();
		String cid = reqE.getClientId();
		TerminalGd t = this.terminalManager.getTerminal(tid);
		String oldSid = t.getSessionId(false);

		this.terminalManager.bindingSession(tid, sid);
		this.clientManager.bindingSession(cid, sid);
		if (oldSid != null) {
			this.sessionManager.removeEntity(oldSid);//
		}
		if (pts != null) {
			res.setPayloads(pts);
		}
		res.setPayload("sessionId", sid);
		res.setPayload("accountId", aid);

	}

	@Handle("unbinding")
	public void handleUnbinding(ResponseI res, TerminalMsgReceiveEW reqE, TerminalMsgSendEW resE, MessageI req) {
		String sid = reqE.getMessage().getString("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {
			throw new FsException("todo no session ");
		}
		String tid = reqE.getTerminalId();
		String cid = reqE.getClientId();
		this.unbinding(res, cid, tid, sid);
	}

	protected void unbinding(ResponseI res, String cid, String tid, String sid) {
		this.terminalManager.unBindingSession(tid);
		this.clientManager.unBindingSession(cid);
		this.sessionManager.removeEntity(sid);

	}
}
