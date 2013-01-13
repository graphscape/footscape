/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.support.WorkerTestBase;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends WorkerTestBase<ExpTestWorker> {

	public void testUserExp() {
		this.delayTestFinish(timeoutMillis * 10);
	}

	protected ExpTestWorker newWorker(UserLoginEvent le) {
		return new ExpTestWorker("user1", "user1@some.com", "user1", 2);

	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	protected void done() {
		this.finishTest();
	}

}
