/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.test.cases;

import java.util.HashSet;
import java.util.Set;

import com.fs.commons.api.Event;
import com.fs.commons.api.event.AfterActiveEvent;
import com.fs.commons.api.event.BeforeActiveEvent;
import com.fs.commons.impl.test.cases.support.TestBase;
import com.fs.commons.impl.test.config.TestConfigurable;
import com.fs.commons.impl.test.config.TestConfigurable2;

/**
 * @author wuzhen
 * 
 */
public class SPITest extends TestBase {
	private Set<Class> eventClass = new HashSet<Class>();

	private Set<Class> sourceClass = new HashSet<Class>();

	public void testSPI() throws Exception {
		assertTrue(sourceClass.contains(TestConfigurable.class));
		assertTrue(sourceClass.contains(TestConfigurable2.class));

		assertTrue(eventClass.contains(BeforeActiveEvent.class));
		assertTrue(eventClass.contains(AfterActiveEvent.class));

	}

	@Override
	public void onEvent(Event e) {
		this.eventClass.add(e.getClass());
		this.sourceClass.add(e.getSource().getClass());

	}
}
