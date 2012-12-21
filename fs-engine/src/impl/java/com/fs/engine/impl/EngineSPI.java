/**
 * Jun 15, 2012
 */
package com.fs.engine.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.impl.converter.HandleContextC;
import com.fs.engine.impl.converter.RequestC;
import com.fs.engine.impl.converter.ResponseC;
import com.fs.engine.impl.scenario.ScenarioFactory;

/**
 * @author wuzhen
 * 
 */
public class EngineSPI extends SPISupport {

	/** */
	public EngineSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {

		ac.active("engineFactory");
		// add handle context converter
		ConverterI.FactoryI cf = ac.getContainer().find(
				ConverterI.FactoryI.class, true);

		cf.addConverter(new RequestC(cf));
		cf.addConverter(new ResponseC(cf));//
		cf.addConverter(new HandleContextC(cf));//
		// add scenario facotry
		ScenarioFactory sf = new ScenarioFactory();
		ac.active("SCENARIO_FACTORY", sf);
	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
