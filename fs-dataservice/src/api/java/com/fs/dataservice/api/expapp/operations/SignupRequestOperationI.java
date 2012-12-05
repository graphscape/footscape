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
public interface SignupRequestOperationI extends OperationI {

	public SignupRequestOperationI execute(String email, String password,
			String nick);

}
