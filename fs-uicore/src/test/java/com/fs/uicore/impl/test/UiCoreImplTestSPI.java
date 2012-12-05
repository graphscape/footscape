/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;

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
	public void active(ActiveContext ac) {
		// ac.getContainer().find(ConfigFactoryI.class).newPopulator().active(ac).type("ha")
		ac.getContainer().find(DispatcherI.class).populator("handler")
				.active(ac).cfgId(this.getId() + ".Object.DISPATCHER")
				.populate();

	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
