/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import junit.framework.TestCase;

import org.junit.Before;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DeleteAllOperationI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.expector.gridservice.api.mock.MockExpectorClientFactory;
import com.fs.expector.gridservice.impl.ExpectorGridServiceSPI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {
	protected SPIManagerI sm;
	protected ContainerI container;

	protected DataServiceI dataService;

	protected ServiceEngineI engine;

	protected MockExpectorClientFactory cfactory;
	
	@Before
	public void setUp() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.dataService = this.container.find(DataServiceI.class, true);
		this.engine = this.container.find(EngineFactoryI.class, true)
				.getEngine(ExpectorGridServiceSPI.ENAME_UISERVER);
		this.cfactory = MockExpectorClientFactory.getInstance(this.container);//
		this.cleanDb();
	}

	protected void cleanDb() {
		DeleteAllOperationI dao = this.dataService
				.prepareOperation(DeleteAllOperationI.class);
		dao.execute().getResult().assertNoError();

	}
	

	protected void dumpDb() {
		DumpOperationI op = this.dataService
				.prepareOperation(DumpOperationI.class);
		op.execute().getResult().assertNoError();
	}

}
