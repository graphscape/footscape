/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.FormDataAP;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class FormSubmitAP extends FormDataAP {

	private Path path;

	public FormSubmitAP(ContainerI c, Path path) {
		this(c, path, null);
	}

	public FormSubmitAP(ContainerI c, Path path, String form) {
		super(c, form);
		this.path = (path);
	}

	@Override
	public void handle(ActionEvent ae) {
		MsgWrapper req = this.newRequest(path);
		this.processFormData(ae, req);
		this.sendMessage(req);
	}

}
