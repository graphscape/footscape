/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.expapp.impl.result;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.dataservice.api.expapp.result.CooperRequestResultI;

public class CooperRequestResultImpl extends ResultSupport<CooperRequestResultI,String> implements
		CooperRequestResultI {

	/**
	 * @param ds
	 */
	public CooperRequestResultImpl(DataServiceI ds) {
		super(ds);
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public String getCooperRequestId() {
		//
		return (String) this.getProperty(COOPER_REQUEST_ID);
	}

}