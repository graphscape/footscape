/**
 * Jun 20, 2012
 */
package com.fs.filesystem.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.filesystem.api.FileSystemI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected SPIManagerI sm;
	protected ContainerI container;
	protected FileSystemI fs;

	/* */
	@Override
	protected void setUp() throws Exception {
		sm = SPIManagerI.FACTORY.get();
		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.fs = this.container.find(FileSystemI.class);
	}

	/* */
	@Override
	public void setName(String name) {

		super.setName(name);

	}

}
