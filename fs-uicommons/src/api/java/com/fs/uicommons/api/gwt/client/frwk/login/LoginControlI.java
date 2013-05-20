package com.fs.uicommons.api.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

public interface LoginControlI extends ControlI {

	/**
	 * @return
	 */
	public LoginViewI openLoginView(boolean show);
	
	public PasswordResetViewI openPasswordResetView();

}
