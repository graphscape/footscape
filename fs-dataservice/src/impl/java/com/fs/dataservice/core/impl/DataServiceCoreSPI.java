/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.core.impl.elastic.ElasticDataServiceImpl;

/**
 * @author wu
 * 
 */
public class DataServiceCoreSPI extends SPISupport {

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
		ac.active("dataService");

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doDeactive(ActiveContext ac) {
		//

	}

}
