/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.gridservice.commons.impl.handler.local.terminal;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.Handle;
import com.fs.gridservice.commons.api.support.TerminalEventHandlerSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgSendEW;

/**
 * @author wu
 * 
 */
public class TermialMessageSendEventHandler extends TerminalEventHandlerSupport {

	/*
	 * Dec 16, 2012
	 */
	@Handle("send")
	public void handleSend(TerminalMsgSendEW reqE, MessageI req) {
		//
		MessageI msg = reqE.getMessage();

		String termId = reqE.getTerminalId();

		this.terminalManager.sendMessage(termId, msg);

	}
}