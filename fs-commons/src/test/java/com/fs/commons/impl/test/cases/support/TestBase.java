/**
 * Jun 20, 2012
 */
package com.fs.commons.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;

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
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
	}

	/* */
	@Override
	public void setName(String name) {

		super.setName(name);

	}

}
