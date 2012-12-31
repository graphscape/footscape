/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 14, 2012
 */
package com.fs.gridservice.commons.api.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class TerminalMsgReseiveEventHandler extends TerminalEventHandlerSupport {

	/*
	 * Dec 14, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);

	}

	protected void sendResponseFailureMessage(TerminalMsgReceiveEW source) {
		String tId = source.getTerminalId();
		Path path = source.getMessage().getPath();
		path = path.getSubPath("failure");
		this.sendTextMessage(tId, path, null);

	}

	protected void sendResponseSuccessMessage(TerminalMsgReceiveEW source) {

	}

	protected MessageI newResponseSuccessMessage(TerminalMsgReceiveEW source) {
		String tId = source.getTerminalId();
		Path path = source.getMessage().getPath();
		path = path.getSubPath("success");
		MessageI msg = new MessageSupport();
		msg.setHeader(MessageI.HK_PATH, path.toString());
		msg.setHeader("terminalId", tId);

		return msg;
	}

	protected void sendResponseSuccessMessage(TerminalMsgReceiveEW source,
			String text) {
		MessageI msg = this.newResponseSuccessMessage(source);
		msg.setPayload("text", text);
		this.sendMessage(msg);
	}

}
