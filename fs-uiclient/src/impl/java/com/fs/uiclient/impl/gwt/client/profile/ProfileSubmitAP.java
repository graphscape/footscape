/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.profile;

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
public class ProfileSubmitAP extends FormDataAP {

	/**
	 * @param c
	 */
	public ProfileSubmitAP(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(Path.valueOf("/profile/submit"));
		ModelI m = (ModelI) ae.getProperty("formModel", true);
		this.processFormData(m, req);
		this.sendMessage(req);
	}

}
