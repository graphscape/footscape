/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.endpoint;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class ServerIsReadyMH implements MessageHandlerI {

	protected EndPointI endpoint;

	public ServerIsReadyMH(EndPointI ep) {
		this.endpoint = ep;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void handle(MessageData t) {
		// send binding
		// see gridservice's TerminalXXXHandler
		MessageData req = new MessageData("/terminal/binding");
		req.setPayload("sessionId", StringData.valueOf(endpoint.getSessionId()));
		this.endpoint.sendMessage(req);
	}

}
