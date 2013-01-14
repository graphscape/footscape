/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;

/**
 * @author wu
 * 
 */
public class UserExpSelectAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI) ae.getSource();
		UserExpModel um = c.getModel();
		String expId = um.getExpId();
		UserExpListControlI uc = this.getControl(ae, UserExpListControlI.class, true);
		uc.select(expId);

	}

}
