/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 16, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.auth;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointUnbondEvent;
import com.fs.uicore.api.gwt.client.mock.MockSignupEvent;
import com.fs.uicore.api.gwt.client.mock.MockUiClient;

public class LoginTest extends TestBase {

	private String nick = "user1";
	private String email = nick + "@domain.com";
	private String pass = nick;

	LoginView loginView;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

	}

	private MockUiClient mc;

	public void testLogin() {
		AccountsLDW accs = AccountsLDW.getInstance();
		accs.invalid();

		this.finishing.add("loginView");

		this.finishing.add("bound-anonymous");

		this.finishing.add("signuprequest");

		this.finishing.add("loginrequest");

		this.finishing.add("bound");

		// this.finishing.add("5.unbound");//

		mc = new MockUiClient(this.client);
		this.delayTestFinish(timeoutMillis * 100);

	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	protected void onEvent(Event e) {
		//
		super.onEvent(e);
		if (e instanceof EndpointBondEvent) {
			this.onBondEvent((EndpointBondEvent) e);
		} else if (e instanceof EndpointUnbondEvent) {
			// this.onUnbondEvent((EndpointUnbondEvent) e);
		}
	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {

	}

	@Override
	protected void onAttachedEvent(AttachedEvent e) {
		super.onAttachedEvent(e);
		UiObjectI src = e.getSource();
		if (src instanceof LoginView) {
			this.onLoginViewAttached((LoginView) src);
		}
	}

	/**
	 * Jan 2, 2013
	 */
	private void onLoginViewAttached(final LoginView src) {

		this.tryFinish("loginView");
		this.loginView = src;

	}

	/**
	 * Jan 2, 2013
	 */
	private void onBondEvent(EndpointBondEvent e) {
		//
		UserInfo ui = this.client.getEndpoint().getUserInfo();
		if (ui.isAnonymous()) {

			this.tryFinish("bound-anonymous");

			this.mc.addHandler(MockSignupEvent.TYPE, new EventHandlerI<MockSignupEvent>() {

				@Override
				public void handle(MockSignupEvent t) {
					LoginTest.this.login();
				}
			});
			this.mc.signup(email, nick, pass);
			this.tryFinish("signuprequest");
		} else {
			String email = ui.getString("email");
			assertEquals(this.email, email);
			this.tryFinish("bound");
		}
	}

	private void login() {
		LoginModelI lm = this.loginView.getModel();
		StringEditorI unameE = this.loginView.find(StringEditorI.class, "email", true);
		unameE.input((this.email));

		StringEditorI passwordE = this.loginView.find(StringEditorI.class, "password", true);
		passwordE.input((this.pass));

		this.loginView.clickAction(Actions.A_LOGIN_SUBMIT);// submit
		this.tryFinish("loginrequest");

	}

}
