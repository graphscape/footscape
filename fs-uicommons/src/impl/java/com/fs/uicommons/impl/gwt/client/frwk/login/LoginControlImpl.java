/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.frwk.BodyView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;

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

	@Override
	public LoginModelI getOrCreateLoginModel() {

		LoginModelI rt = this.getClient(true).getRootModel().find(LoginModelI.class, false);
		if (rt != null) {
			return rt;
		}
		rt = new LoginModel("login");
		this.getClient(true).getRootModel().child(rt);//

		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI#openLoginView()
	 */
	@Override
	public void openLoginView() {
		LoginModelI lm = this.getOrCreateLoginModel();
		this.getOrCreateLoginView(lm);
	}

	public LoginView getOrCreateLoginView(LoginModelI lm) {
		BodyView bv = this.getBodyView();
		Path path = Path.valueOf("/login/view");
		LoginView lv = bv.getItem(path, false);
		if (lv == null) {
			lv = new LoginView("login", this.getContainer());
			lv.model(lm);
			bv.addItem(path, lv);
		}
		return lv;
	}

}
