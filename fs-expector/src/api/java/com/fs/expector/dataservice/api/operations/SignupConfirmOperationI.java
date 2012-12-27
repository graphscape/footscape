/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.operations;

import com.fs.dataservice.api.core.OperationI;

/**
 * @author wu
 * 
 */
public interface SignupConfirmOperationI extends OperationI {
	public SignupConfirmOperationI execute(String reqUid, String confirmCode);//

}
