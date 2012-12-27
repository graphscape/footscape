/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 22, 2012
 */
package com.fs.gridservice.commons.impl.handler.global.ping;

import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.support.TerminalMsgReseiveEventHandler;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class PingHandler extends TerminalMsgReseiveEventHandler {

	@Handle("ping")
	public void handlePing(TerminalMsgReceiveEW reqE, RequestI req) {
		MessageI msg = reqE.getMessage();
		String text = msg.getString("text", true);

		this.sendResponseSuccessMessage(reqE, text);

	}
}
