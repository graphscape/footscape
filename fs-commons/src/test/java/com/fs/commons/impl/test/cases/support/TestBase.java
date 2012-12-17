/**
 * Jun 20, 2012
 */
package com.fs.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.Event;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.event.ListenerI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;
	protected ContainerI container;

	/* */
	@Override
	protected void setUp() throws Exception {
		sm = SPIManagerI.FACTORY.get();
		sm.getContainer().getEventBus().addListener(Event.class, new ListenerI<Event>() {

			@Override
			public void handle(Event t) {
				TestBase.this.onEvent(t);
			}
		});
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
	}

	public void onEvent(Event e) {

	}

	/* */
	@Override
	public void setName(String name) {

		super.setName(name);

	}

}
