/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceFactoryI;

/**
 * @author wu
 * 
 */
public class DataServiceCoreSPI extends SPISupport {

	public static final int shutdownLoop = 30;

	/**
	 * @param id
	 */
	public DataServiceCoreSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {
		ac.active("dataServiceFactory");

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//
		if (loop == this.shutdownLoop) {
			DataServiceFactoryI dsf = this.container.find(DataServiceFactoryI.class, true);
			dsf.close();
		}

	}

}
