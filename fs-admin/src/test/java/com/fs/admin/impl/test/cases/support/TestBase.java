/**
 * Jul 31, 2012
 */
package com.fs.admin.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {
	protected static SPIManagerI SPIM;

	protected ContainerI container;

	@Override
	public void setUp() {
		if (SPIM == null) {
			SPIM = SPIManagerI.FACTORY.get();
			SPIM.load("/boot/test-spim.properties");
		}
		this.container = SPIM.getContainer();

	}
}
