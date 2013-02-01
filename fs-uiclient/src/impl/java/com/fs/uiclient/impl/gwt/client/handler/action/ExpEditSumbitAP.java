/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.FormDataAP;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ExpEditSumbitAP extends FormDataAP {

	/**
	 * @param c
	 */
	public ExpEditSumbitAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(Path.valueOf("/expe/submit"));
		this.processFormData(ae, req);
		this.sendMessage(req);
	}

	/*
	 * Oct 21, 2012
	 */

}
