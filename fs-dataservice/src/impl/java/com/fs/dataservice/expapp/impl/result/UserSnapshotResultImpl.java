/**
 *  Nov 28, 2012
 */
package com.fs.dataservice.expapp.impl.result;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.dataservice.api.expapp.result.UserSnapshotResultI;

public class UserSnapshotResultImpl extends
		ResultSupport<UserSnapshotResultI, String> implements
		UserSnapshotResultI {

	/**
	 * @param ds
	 */
	public UserSnapshotResultImpl(DataServiceI ds) {
		super(ds);
	}

}