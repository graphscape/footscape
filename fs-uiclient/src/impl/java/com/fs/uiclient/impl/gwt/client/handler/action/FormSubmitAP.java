/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
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
public class FormSubmitAP extends FormDataAP {

	private Path path;

	public FormSubmitAP(String path) {
		this(path, null);
	}

	public FormSubmitAP(String path, String form) {
		super(form);
		this.path = Path.valueOf(path);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(path);
		ModelI m = ((ControlI) ae.getSource()).getModel();
		this.processFormData(m, req);
		this.sendMessage(ae, req);
	}

}
