/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.api.result;

import com.fs.dataservice.api.core.ResultI;

public interface CooperConfirmResultI extends
		ResultI<CooperConfirmResultI, Object> {

	public static final String COOPER_CONFIRM_ID = "cooperConfirmId";

	public String getCooperConfirmId();// account is account id

}