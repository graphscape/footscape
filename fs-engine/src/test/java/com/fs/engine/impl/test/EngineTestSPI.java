/**
 * Jun 20, 2012
 */
package com.fs.engine.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.factory.PopulatorI;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;

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
	public void doActive(ActiveContext ac) {
		ServiceEngineI eg = ac.getContainer().find(EngineFactoryI.class, true)
				.getEngine("engine-0");
		DispatcherI dp = eg.getDispatcher();

		PopulatorI pp = dp.populator("handler").active(ac)
				.cfgId(this.id + ".Object.DISPATCHER");
		pp.populate();

		//

		PopulatorI pp2 = eg.populator("filter").active(ac)
				.cfgId(this.id + ".ENGINE");
		pp2.populate();
		// test scenario

	}

	/*
	
	 */
	@Override
	public void doDeactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
