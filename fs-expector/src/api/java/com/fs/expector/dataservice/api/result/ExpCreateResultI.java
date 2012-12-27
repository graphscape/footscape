/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.api.result;

import com.fs.dataservice.api.core.ResultI;

public interface ExpCreateResultI extends ResultI<ExpCreateResultI, String> {

	public String getExpId();// account is account uniqueId

}