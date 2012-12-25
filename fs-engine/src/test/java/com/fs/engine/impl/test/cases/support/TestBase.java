/**
 * Jun 19, 2012
 */
package com.fs.engine.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.service.ServiceI;
import com.fs.engine.api.EngineAPI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {
	protected SPIManagerI sm;
	protected ServiceI<RequestI, ResponseI> service;
	protected ContainerI container;

	@Override
	public void setUp() {
		sm = SPIManagerI.FACTORY.get();

		sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.service = this.sm.getContainer().find(EngineFactoryI.class, true)
				.getEngine("0");//

	}

	@Override
	public void tearDown() {
		// sm.shutdown();
	}
}
