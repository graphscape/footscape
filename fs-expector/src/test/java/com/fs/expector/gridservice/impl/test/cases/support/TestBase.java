/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import junit.framework.TestCase;

import org.junit.Before;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.expector.gridservice.api.mock.MockExpectorClientFactory;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {
	protected SPIManagerI sm;
	protected ContainerI container;

	protected DataServiceI dataService;

	protected MessageServiceI engine;

	protected MockExpectorClientFactory cfactory;

	@Before
	public void setUp() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

		this.cfactory = MockExpectorClientFactory.getInstance(this.container);//

	}

	protected void dumpDb() {
		DumpOperationI op = this.dataService.prepareOperation(DumpOperationI.class);
		op.execute().getResult().assertNoError();
	}

}
