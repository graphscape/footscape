/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class CooperConfirmAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		String action = ae.getAction();
		ControlI c = (ControlI) ae.getSource();
		ActionModelI am = c.getModel().getChild(ActionModelI.class, action, true);

		String crId = (String) am.getValue("crId");
		if (crId == null) {
			throw new UiException("missing action parameter crId");
		}
		MsgWrapper req = this.newRequest(Path.valueOf("/cooper/confirm"));
		req.getPayloads().setProperty("cooperRequestId", (crId));
		this.sendMessage(ae, req);
	}

}
