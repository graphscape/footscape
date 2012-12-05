/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.dataservice.api.expapp.operations;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.result.VoidResultI;

/**
 * @author wu
 * 
 */
public interface UserSnapshotOperationI extends
		OperationI<UserSnapshotOperationI, VoidResultI> {
	
	public static final String PK_ACCOUNTID = "accountId";

	public UserSnapshotOperationI accountId(String accId);

}
