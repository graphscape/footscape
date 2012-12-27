/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.api.result;

import com.fs.dataservice.api.core.ResultI;

public interface CooperRequestResultI extends
		ResultI<CooperRequestResultI, String> {
	public static final String COOPER_REQUEST_ID = "cooperRequestId";

	public String getCooperRequestId();// account is account uniqueId

}