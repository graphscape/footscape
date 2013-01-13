/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.support.WorkerTestBase;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;

/**
 * @author wuzhen
 * 
 */
public class ActivityTest extends WorkerTestBase<ActivityTestWorker> {

	public void testActivityOpen() {

		this.delayTestFinish(timeoutMillis);

	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	protected ActivityTestWorker newWorker(UserLoginEvent le) {
		//
		return new ActivityTestWorker("user1", "user1@some.com", "user1", 3);
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	protected void done() {
		//
		this.finishTest();
	}

}
