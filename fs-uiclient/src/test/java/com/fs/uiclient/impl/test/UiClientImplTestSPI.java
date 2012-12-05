/**
 * Jun 24, 2012
 */
package com.fs.uiclient.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;

/**
 * @author wu
 * 
 */
public class UiClientImplTestSPI extends SPISupport {

	/** */
	public UiClientImplTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		ac.getContainer().find(DispatcherI.class).populator("handler")
				.cfgId(this.getId() + ".Object.DISPATCHER").active(ac)
				.populate();
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
