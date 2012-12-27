/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.impl.result;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.expector.dataservice.api.result.CooperConfirmResultI;

public class CooperConfirmResultImpl extends
		ResultSupport<CooperConfirmResultI, Object> implements
		CooperConfirmResultI {

	/**
	 * @param ds
	 */
	public CooperConfirmResultImpl(DataServiceI ds) {
		super(ds);
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public String getCooperConfirmId() {
		//
		return (String) this
				.getProperty(CooperConfirmResultI.COOPER_CONFIRM_ID);
	}

}