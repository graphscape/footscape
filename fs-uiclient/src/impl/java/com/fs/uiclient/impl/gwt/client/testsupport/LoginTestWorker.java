/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginView;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public class LoginTestWorker extends SignupTestWorker {

	
	public LoginTestWorker(String nick, String email, String pass) {
		super(nick, email, pass);
	}

	protected LoginView loginView;

	@Override
	protected void onSignup(String email, String pass) {
		LoginModelI lm = this.loginView.getModel();

		LoginControlI lc = this.manager.getControl(LoginControlI.class, true);
		FormView fv = this.loginView.find(FormView.class, "default", true);

		EditorI passwordE = fv.find(EditorI.class, "password", true);
		passwordE.input((this.password));
		EditorI emailE = fv.find(EditorI.class, "email", true);
		emailE.input((this.email));

		this.loginView.clickAction(Actions.A_LOGIN_SUBMIT);
	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof EndpointBondEvent) {
			this.onBond((EndpointBondEvent) e);
		}
	}

	/**
	 * Jan 3, 2013
	 */
	private void onBond(EndpointBondEvent e) {
		//
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {
			this.onAnonymousUserLogin();
		} else {
			this.onRegisterUserLogin(ui);
		}
	}

	/**
	 * Jan 3, 2013
	 */
	protected void onRegisterUserLogin(UserInfo ui) {

	}

	/**
	 * Jan 3, 2013
	 */
	private void onAnonymousUserLogin() {

	}

	@Override
	public void onAttachedEvent(AttachedEvent ae) {
		super.onAttachedEvent(ae);
		UiObjectI obj = ae.getSource();
		if (obj instanceof LoginView) {
			this.onLoginView((LoginView) obj);
		}
	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		super.onSuccessMessageEvent(e);
	}

	/**
	 * @param obj
	 */
	protected void onLoginView(LoginView obj) {
		this.loginView = obj;
	}

	/**
	 * 
	 */

}
