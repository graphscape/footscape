/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends TestBase {
	ExpTestWorker worker;

	public void testUserExp() {
		worker = new ExpTestWorker("user1", "user1@some.com", "user1", 2);

		this.finishing.add("all.created");
		this.delayTestFinish(timeoutMillis * 10);

	}

	/*
	 * Jan 12, 2013
	 */
	@Override
	public void onEvent(Event e) {
		super.onEvent(e);
		this.worker.onEvent(e);
		if (this.worker.getTasks().isEmpty()) {
			this.tryFinish("all.created");
		}
	}

}
