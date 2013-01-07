/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class CooperRequest2MeGetAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		String cooperRequestId = null;// TODO
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/incomingCr"));
		req.getPayloads().setProperty("cooperRequestId",
				(cooperRequestId));

	}

}
