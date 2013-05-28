/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.grounder.dataservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.grounder.dataservice.api.wrapper.Activity;

/**
 * @author wu
 * 
 */
public class GrounderDsSPI extends SPISupport {

	/**
	 * @param id
	 */
	public GrounderDsSPI(String id) {
		super(id);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {

		DataServiceFactoryI dsf = ac.getContainer().find(DataServiceFactoryI.class, true);
		// Configurations
		DataSchema cfs = dsf.getSchema();

		Activity.config(cfs);
		
		//facade
		//ac.active("expectorDsFacade", new ExpectorDsFacadeImpl());
	}

	/*
	 *Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		// 
		
	}

}
