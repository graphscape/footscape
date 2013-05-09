/**
 * Jun 20, 2012
 */
package com.fs.webserver.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected static SPIManagerI sm;
	protected ContainerI container;

	/* */
	@Override
	protected void setUp() throws Exception {
		if (sm != null) {
			return;
		}

		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();

	}

	@Override
	protected void tearDown() {

	}

	/* */
	@Override
	public void setName(String name) {

		super.setName(name);

	}

}
