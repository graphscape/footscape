/**
 * Jun 20, 2012
 */
package com.fs.engine.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.scenario.ScenarioI;

/**
 * @author wuzhen
 * 
 */
public class EngineTestSPI extends SPISupport {

	/**
	 * @param id
	 */
	public EngineTestSPI(String id) {
		super(id);
	}

	/*
	
	 */
	@Override
	public void active(ActiveContext ac) {
		DispatcherI dp = ac.getContainer().find(DispatcherI.class, true);
		PopulatorI pp = dp.populator("handler").active(ac)
				.cfgId(this.id + ".Object.DISPATCHER");
		pp.populate();

		//
		ServiceEngineI eg = ac.getContainer().find(ServiceEngineI.class, true);

		PopulatorI pp2 = eg.populator("filter").active(ac)
				.cfgId(this.id + ".ENGINE");
		pp2.populate();
		// test scenario
		ScenarioI.FactoryI sf = ac.getContainer().find(
				ScenarioI.FactoryI.class, true);

		sf.createScenario(ac, this.getId() + ".test-scenario");
	}

	/*
	
	 */
	@Override
	public void deactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
