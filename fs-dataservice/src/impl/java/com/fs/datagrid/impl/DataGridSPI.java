/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.datagrid.impl.hazelcast.DgFactoryHC;

/**
 * @author wuzhen
 * 
 */
public class DataGridSPI extends SPISupport {

	/**
	 * @param id
	 */
	public DataGridSPI(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#active(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void active(ActiveContext ac) {
		DgFactoryHC df = new DgFactoryHC();
		ac.activitor().name("DG_FACTORY").object(df).active();
		
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
