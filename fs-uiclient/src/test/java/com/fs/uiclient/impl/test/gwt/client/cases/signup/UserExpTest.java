/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uiclient.impl.test.gwt.client.cases.support.ExpTestBase;

/**
 * @author wuzhen
 * 
 */
public class UserExpTest extends ExpTestBase {

	public void testUserExp() {
		this.finishing.add("all.created");
		this.delayTestFinish(timeoutMillis * 10);

	}

	@Override
	protected void onNewExpView(int idx, UserExpView e) {
		if (idx + 1 == this.totalExp()) {
			this.tryFinish("all.created");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.ExpTestBase#totalExp()
	 */
	@Override
	protected int totalExp() {
		// TODO Auto-generated method stub
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.impl.test.gwt.client.cases.support.ExpTestBase#expText
	 * (int)
	 */
	@Override
	protected String expText(int idx) {
		// TODO Auto-generated method stub
		return "exp-" + idx;
	}

}
