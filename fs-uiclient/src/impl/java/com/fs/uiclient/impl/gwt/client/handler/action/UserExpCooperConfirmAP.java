/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.support.ActionHandlerSupport2;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpCooperConfirmAP extends ActionHandlerSupport2 {

	/**
	 * @param c
	 */
	public UserExpCooperConfirmAP(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		CooperControlI cc = this.getControl(CooperControlI.class, true);//
		String crId = (String) ae.getProperty("incomingCrId", true);

		cc.cooperConfirm(crId);
	}

}
