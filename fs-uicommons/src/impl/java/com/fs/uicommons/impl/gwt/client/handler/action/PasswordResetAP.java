/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *
 */
public class PasswordResetAP extends ActionHandlerSupport{

	/**
	 * @param c
	 */
	public PasswordResetAP(ContainerI c) {
		super(c);
	}

	/*
	 *Mar 31, 2013
	 */
	@Override
	public void handle(ActionEvent t) {
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		PasswordResetViewI pv = lc.openPasswordResetView();
		pv.clearErrorInfo();
		MsgWrapper req = this.newRequest(Path.valueOf("password/reset"));
		String pfId = pv.getPfId();
		String np = pv.getNewPassword();
		req.setPayload("pfId", pfId);
		req.setPayload("newPassword", np);
		this.sendMessage(req);
		
	}

}
