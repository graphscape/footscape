/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI;
import com.fs.uicore.api.gwt.client.config.UiConfiguration;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.event.StateChangeEvent;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class CfgFactoryTest extends TestBase {

	public void testCfg() {

		this.delayTestFinish(10 * 1000);

	}

	@Override
	protected void onEvent(Event e) {
		super.onEvent(e);
		if (e.isMatch(StateChangeEvent.TYPE, ConfigurationFactoryI.class)) {

			System.out.println("state chaged");

			ConfigurationFactoryI cf = (ConfigurationFactoryI) e.getSource();
			UiConfiguration cfg = cf.getConfiguration(
					"com.fs.uicore.CfgFactory", true);
			// TODO
			this.finishTest();
		}
	}

}
