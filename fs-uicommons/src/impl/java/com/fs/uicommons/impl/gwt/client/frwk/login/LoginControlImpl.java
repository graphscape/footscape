/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wuzhen
 * 
 */
public class LoginControlImpl extends ControlSupport implements LoginControlI {

	/**
	 * @param name
	 */
	public LoginControlImpl(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI#openLoginView()
	 */
	@Override
	public LoginViewI openLoginView() {

		BodyViewI bv = this.getBodyView();
		LoginView lv = bv.getItem(Constants.LOGIN_VIEW, false);
		if (lv == null) {
			lv = new LoginView(this.getContainer(), "login");

			bv.addItem(Constants.LOGIN_VIEW, lv);
		}
		return lv;
	}

}
