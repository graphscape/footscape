/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.CollectionTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.LoginTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.TestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.support.WorkerTestBase;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends WorkerTestBase<TestWorker> {

	public void testUserExp() {
		this.delayTestFinish(timeoutMillis * 10);
	}

	@Override
	protected TestWorker newWorker(UserLoginEvent le) {
		CollectionTestWorker worker = new CollectionTestWorker().addTestWorker(
				new LoginTestWorker("user1", "user1@some.com", "user1"))//
				.addTestWorker(new ExpTestWorker(2))//

		;
		return worker;

	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	protected void done() {
		this.finishTest();
	}

}
