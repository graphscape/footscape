/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 30, 2012
 */
package com.fs.dataservice.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceI;

/**
 * @author wu
 * 
 */
public class DsTestSPI extends SPISupport {

	/**
	 * @param id
	 */
	public DsTestSPI(String id) {
		super(id);
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {
		DataServiceI ds = ac.getContainer().find(DataServiceI.class, true);
		MockNode.config(ds.getConfigurations());
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	protected void doDeactive(ActiveContext ac) {
		//

	}

}
