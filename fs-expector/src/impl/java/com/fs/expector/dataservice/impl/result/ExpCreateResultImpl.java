/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.impl.result;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.expector.dataservice.api.result.ExpCreateResultI;

public class ExpCreateResultImpl extends
		ResultSupport<ExpCreateResultI, String> implements ExpCreateResultI {
	public static final String EXPID = "expId";

	/**
	 * @param ds
	 */
	public ExpCreateResultImpl(DataServiceI ds) {
		super(ds);
	}

	/*
	 * Nov 4, 2012
	 */
	@Override
	public String getExpId() {
		//
		return (String) this.getProperty(EXPID);
	}

}