/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.core.impl.hazelcast.DgFactoryHC;

/**
 * @author wuzhen
 * 
 */
public class GsCoreSPI extends SPISupport {

	/**
	 * @param id
	 */
	public GsCoreSPI(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#active(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void doActive(ActiveContext ac) {
		DgFactoryHC df = new DgFactoryHC();
		ac.activitor().name("DG_FACTORY").object(df).active();

	}

	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
