/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpSelectAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public UserExpSelectAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI) ae.getSource();
		String expId = (String) ae.getProperty("expId", true);
		UserExpListControlI uc = this.getControl(UserExpListControlI.class, true);
		uc.select(expId);

	}

}
