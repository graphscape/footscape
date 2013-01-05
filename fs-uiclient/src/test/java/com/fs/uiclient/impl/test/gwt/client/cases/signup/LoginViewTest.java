/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.test.gwt.client.cases.support.LoginTestBase;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wuzhen
 * 
 */
public class LoginViewTest extends LoginTestBase {

	public void testSignup() {

		this.finishing.add("login.ok");
		this.delayTestFinish(timeoutMillis);

	}

	@Override
	protected void onRegisterUserLogin(UserInfo ui) {
		this.tryFinish("login.ok");
	}
}
