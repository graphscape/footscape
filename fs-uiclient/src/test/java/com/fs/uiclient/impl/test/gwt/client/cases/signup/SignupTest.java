/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.test.gwt.client.cases.support.SignupTestBase;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends SignupTestBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.SignupTestBase#onSignup
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	protected void onSignup(String email, String pass) {
		this.tryFinish("signup.ok");
	}

	public void testSignup() {

		this.finishing.add("signup.ok");
		this.delayTestFinish(timeoutMillis);

	}

}
