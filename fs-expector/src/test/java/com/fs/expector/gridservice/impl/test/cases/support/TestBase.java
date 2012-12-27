/**
 * Jun 22, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPIManagerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DeleteAllOperationI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.scenario.ScenarioI;
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

	@Before
	public void setUp() {
		this.sm = SPIManagerI.FACTORY.get();
		this.sm.load("/boot/test-spim.properties");
		this.container = sm.getContainer();
		this.dataService = this.container.find(DataServiceI.class, true);
		this.engine = this.container.find(EngineFactoryI.class, true)
				.getEngine(ExpectorGridServiceSPI.ENAME_UISERVER);
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

	public void runScenario(String cfgId) {
		ScenarioI.FactoryI sf = this.container.find(ScenarioI.FactoryI.class,
				true);
		ScenarioI s = sf.getScenario(cfgId);
		if (s == null) {
			throw new FsException("no scenario is configured:" + cfgId);
		}
		ScenarioI.ContextI sc = s.run();
		ErrorInfos eis = sc.getAllErrorInfos();
		if (eis.hasError()) {
			System.out.println(eis.toString());
			Assert.assertTrue(
					"scenario failed:" + cfgId + ",eis:" + eis.toString() + "",
					false);
		}
	}
}
