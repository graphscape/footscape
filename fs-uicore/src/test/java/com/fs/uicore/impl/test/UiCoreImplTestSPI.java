/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.EngineFactoryI;
import com.fs.engine.api.ServiceEngineI;

/**
 * @author wu
 * 
 */
public class UiCoreImplTestSPI extends SPISupport {

	/** */
	public UiCoreImplTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		// ac.getContainer().find(ConfigFactoryI.class).newPopulator().active(ac).type("ha")
		ServiceEngineI e0 = ac.getContainer().find(EngineFactoryI.class, true)
				.getEngine(0);

		e0.getDispatcher().populator("handler").active(ac)
				.cfgId(this.getId() + ".Object.DISPATCHER").populate();

	}

	/* */
	@Override
	public void doDeactive(ActiveContext ac) {
	}

}
