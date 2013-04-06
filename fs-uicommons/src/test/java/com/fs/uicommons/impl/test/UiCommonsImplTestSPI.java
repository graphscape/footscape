/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.GlobalEventDispatcherI;

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
	public void doActive(ActiveContext ac) {
		// ac.getContainer().find(ConfigFactoryI.class).newPopulator().active(ac).type("ha")
		EventDispatcherI ged = ac.getContainer().find(GlobalEventDispatcherI.class, true);

		Configuration cfg = Configuration.properties(this.id + ".globalEventDispatcher");

		ged.getEngine().getDispatcher()
				.addHandlers(cfg.getId(), cfg.getPropertyAsCsv("handlers").toArray(new String[] {}));

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
