/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class CooperConfirmAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public CooperConfirmAP(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {

		String crId = (String) ae.getProperty("crId", true);

		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/confirm"));
		req.getPayloads().setProperty("cooperRequestId", (crId));
		this.sendMessage(req);
	}

}
