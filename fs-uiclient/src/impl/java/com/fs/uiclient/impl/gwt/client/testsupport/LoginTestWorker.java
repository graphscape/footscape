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
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public final class LoginTestWorker extends SignupTestWorker {

	protected LoginView loginView;

	public LoginTestWorker(String nick, String email, String pass) {
		super(nick, email, pass);
		this.tasks.add("login.done");
	}

	@Override
	protected void onSignup(String email, String pass) {
		this.loginView = (LoginView) this.manager.getControl(LoginControlI.class, true).openLoginView(true);

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
			this.tryFinish("login.done");

		}
	}

	/**
	 * Jan 3, 2013
	 */
	private void onAnonymousUserLogin() {

	}

	@Override
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		super.onSuccessMessageEvent(e);
	}

	/**
	 * 
	 */

}
