package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.handler.action.SignupAnonymousAH;
import com.fs.uicommons.impl.gwt.client.handler.action.LoginSubmitAH;
import com.fs.uicommons.impl.gwt.client.handler.action.LogoutAP;

/**
 * auth action is not login action,it will do auth like the submit. login just
 * open the login form.
 * 
 * @author wu
 * 
 */
public class LoginControl extends ControlSupport implements LoginControlI {

	public LoginControl(String name) {
		super(name);
		
	}

}
