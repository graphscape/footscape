/**
 * Jul 15, 2012
 */
package com.fs.integrated.test.gwt.client;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;
import com.fs.uicore.api.gwt.client.event.ClientStartEvent;

/**
 * @author wu
 * 
 */
public class SmokeTest extends TestBase {

	public void testSmoke() {
		this.delayTestFinish(10000);
	}

	public void onEvent(Event e) {
		SimpleEventFilter ef = SimpleEventFilter.valueOf(ClientStartEvent.TYPE,
				UiClientI.class);
		e = ef.filter(e);
		if (e == null) {
			return;
		}
		System.out.println("client started.");
		this.finishTest();
	}
}
