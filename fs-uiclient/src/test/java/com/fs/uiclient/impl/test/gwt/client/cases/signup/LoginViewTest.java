/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.LoginTestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicommons.api.gwt.client.event.RegisterUserLoginEvent;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wuzhen
 * 
 */
public class LoginViewTest extends TestBase {
	LoginTestWorker login;

	public void testSignup() {

		this.finishing.add("login.ok");
		this.delayTestFinish(timeoutMillis);

	}

	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		if (e instanceof UserLoginEvent) {
			UserLoginEvent le = (UserLoginEvent) e;
			if (le.getUserInfo().isAnonymous()) {

				login = new LoginTestWorker("user1", "user1@some.com", "user1");

				login.start(client);
			}
		}
		if (e instanceof RegisterUserLoginEvent) {
			RegisterUserLoginEvent ue = (RegisterUserLoginEvent) e;
			UserInfo ui = ue.getUserInfo();
			this.onRegisterUserLogin(ui);
		}
	}

	protected void onRegisterUserLogin(UserInfo ui) {
		this.tryFinish("login.ok");
	}
}
