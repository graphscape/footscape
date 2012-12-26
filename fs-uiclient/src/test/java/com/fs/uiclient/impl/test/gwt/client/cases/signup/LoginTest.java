/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.test.gwt.client.cases.support.LoginTestBase;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;

/**
 * @author wuzhen
 * 
 */
public class LoginTest extends LoginTestBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.SignupTestBase#onSignup
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	protected void onAfterAuthEvent(AfterAuthEvent e) {
		this.tryFinish("login.ok");
	}

	public void testSignup() {

		this.finishing.add("login.ok");
		this.delayTestFinish(timeoutMillis);
		super.start();

	}

}
