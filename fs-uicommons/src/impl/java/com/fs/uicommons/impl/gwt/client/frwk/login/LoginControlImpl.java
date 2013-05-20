/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.frwk.password.PasswordResetView;
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
	public LoginViewI openLoginView(boolean show) {

		// TODO creater
		BodyViewI bv = this.getBodyView();
		LoginView lv = bv.getItem(UiCommonsConstants.LOGIN_VIEW, false);
		if (lv == null) {
			lv = new LoginView(this.getContainer(), "login");

			bv.addItem(UiCommonsConstants.LOGIN_VIEW, lv);
		}
		if(show){
			bv.select(UiCommonsConstants.LOGIN_VIEW);//			
		}
		return lv;
	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public PasswordResetViewI openPasswordResetView() {
		// TODO creater
		BodyViewI bv = this.getBodyView();
		PasswordResetViewI lv = bv.getItem(UiCommonsConstants.PASSWORDRESET_VIEW, false);
		if (lv == null) {
			lv = new PasswordResetView(this.getContainer());

			bv.addItem(UiCommonsConstants.PASSWORDRESET_VIEW, lv);
		}
		bv.select(UiCommonsConstants.PASSWORDRESET_VIEW);//
		return lv;
	}

}
