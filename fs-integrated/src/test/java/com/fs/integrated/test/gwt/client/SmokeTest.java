/**
 * Jul 15, 2012
 */
package com.fs.integrated.test.gwt.client;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class SmokeTest extends TestBase {

	public void testSmoke() {
		this.delayTestFinish(10000);
	}

	public void onEvent(Event e) {
		
		System.out.println("client started.");
		this.finishTest();
	}
}
