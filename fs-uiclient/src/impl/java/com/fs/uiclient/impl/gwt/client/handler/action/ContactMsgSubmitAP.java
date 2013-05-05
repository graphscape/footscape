/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.FormDataAP;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ContactMsgSubmitAP extends FormDataAP {

	/**
	 * @param c
	 */
	public ContactMsgSubmitAP(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openContactUsView(false).clearErrorInfo();
		MsgWrapper req = this.newRequest(Path.valueOf("/cttmsg/submit"));
		this.processFormData(ae, req);
		this.sendMessage(req);
	}

}
