/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ExpEditSumbitAP extends FormDataAP {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(Path.valueOf("/expe/submit"));
		ModelI m = ((ControlI) ae.getSource()).getModel();
		this.processFormData(m, req);
		this.sendMessage(ae, req);
	}

	/*
	 * Oct 21, 2012
	 */

}
