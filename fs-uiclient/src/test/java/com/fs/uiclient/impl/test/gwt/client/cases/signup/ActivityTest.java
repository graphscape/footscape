/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.signup;

import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uiclient.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class ActivityTest extends TestBase {

	ActivityTestWorker worker;
	
	public void testActivityOpen() {
		this.worker = new ActivityTestWorker("user1","user1@some.com","user1",3);
		
		this.delayTestFinish(timeoutMillis);

	}

	/*
	 *Jan 12, 2013
	 */
	@Override
	public void onEvent(Event e) {
		// 
		super.onEvent(e);
		this.worker.onEvent(e);
		if(this.worker.getTasks().isEmpty()){
			this.finishTest();//
		}
	}
	

}
