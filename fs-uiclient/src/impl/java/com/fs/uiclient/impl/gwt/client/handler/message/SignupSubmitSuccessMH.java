/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class SignupSubmitSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public SignupSubmitSuccessMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		MessageData req = res.getSource();
		MainControlI mc = this.getControl(MainControlI.class, true);
		SignupViewI sv = mc.openSignup();
		String email = sv.getEmail();
		String password = sv.getPassword();
		mc.closeSignup();
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		LoginViewI lv = lc.openLoginView();
		lv.setEmail(email);
		lv.setPassword(password);
		

	}

}
