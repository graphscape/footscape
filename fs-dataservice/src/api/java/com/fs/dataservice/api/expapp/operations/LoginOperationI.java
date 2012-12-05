/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.core.OperationI;

/**
 * @author wu
 * 
 */
public interface LoginOperationI extends OperationI {
	public LoginOperationI executeByAccount(String accountUid, String password);//

	public LoginOperationI executeByEmail(String email, String password);//

}
