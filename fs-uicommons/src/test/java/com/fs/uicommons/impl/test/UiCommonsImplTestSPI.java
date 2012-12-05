/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.engine.api.DispatcherI;

/**
 * @author wu
 * 
 */
public class UiCommonsImplTestSPI extends SPISupport {

	/**
	 * @param id
	 */
	public UiCommonsImplTestSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#active(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void active(ActiveContext ac) {
		// ac.getContainer().find(ConfigFactoryI.class).newPopulator().active(ac).type("ha")
		ac.getContainer().find(DispatcherI.class).populator("handler")
				.active(ac).cfgId(this.getId() + ".Object.DISPATCHER")
				.populate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#deactive(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void deactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
