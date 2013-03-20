package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class LoginSubmitAH extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LoginSubmitAH(ContainerI c) {
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
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		req.setProperty("type", ("registered"));
		req.setProperty("isSaved", Boolean.FALSE);

		String email = lm.getEmail();

		String password = lm.getPassword();
		req.setProperty("email", (email));
		req.setProperty("password", (password));
		
		this.getEndpoint().auth(req);
	}

}
