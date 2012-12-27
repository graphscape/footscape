/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.expector.dataservice.api.operations;

import com.fs.dataservice.api.core.OperationI;
import com.fs.expector.dataservice.api.result.UserSnapshotResultI;

/**
 * @author wu
 * 
 */
public interface UserSnapshotOperationI extends
		OperationI<UserSnapshotOperationI, UserSnapshotResultI> {

	public static final String PK_ACCOUNTID = "accountId";
	
	public static final String PK_REFRESH_FOR_SAVE = "refreshForSave";

	public UserSnapshotOperationI accountId(String accId);
	
	public UserSnapshotOperationI refreshForSave(boolean ref);

}
