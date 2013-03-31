package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Request to reset password.
 */
public class PasswordForgotAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public PasswordForgotAP(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		LoginViewI lm = this.findView(LoginViewI.class, true);
		lm.clearErrorInfo();//
		//LoginControlI lc = this.getControl(LoginControlI.class, true);
		//PasswordResetViewI pv = lc.openPasswordResetView();
		//pv.clearErrorInfo();
		
		MsgWrapper req = this.newRequest(Path.valueOf("password/forgot"));

		String email = lm.getEmail();
		req.setPayload("email", email);

		this.sendMessage(req);
	}

}
