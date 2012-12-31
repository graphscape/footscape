/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 14, 2012
 */
package com.fs.gridservice.commons.api.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.ValidatorHandlerSupport;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.client.ClientManagerI;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.gridservice.commons.api.wrapper.internal.InternalMsgEW;

/**
 * @author wu
 * 
 */
public class TerminalEventHandlerSupport extends ValidatorHandlerSupport {

	protected GridFacadeI facade;

	protected SessionManagerI sessionManager;

	protected TerminalManagerI terminalManager;

	protected ClientManagerI clientManager;

	/*
	 * Dec 14, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		this.facade = this.top.find(GridFacadeI.class, true);
		this.sessionManager = this.top.find(SessionManagerI.class, true);
		this.terminalManager = this.facade
				.getEntityManager(TerminalManagerI.class);
		this.clientManager = this.facade.getEntityManager(ClientManagerI.class);
	}

	protected void sendTextMessage(String termId, String path, String text) {
		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_PATH, path);
		msg.setHeader("terminalId", termId);
		msg.setPayload("text", text);//
		this.sendMessage(termId, msg);
	}

	protected void sendMessage(MessageI msg) {
		this.terminalManager.sendMessage(msg);
	}

	protected void sendMessage(String termId, MessageI msg) {
		this.terminalManager.sendMessage(termId, msg);
	}

	/**
	 * Dec 29, 2012
	 */
	protected void raiseEvent(InternalMsgEW sb) {
		this.facade.getGlogalEventQueue().offer(sb.getTarget());//
	}

}
