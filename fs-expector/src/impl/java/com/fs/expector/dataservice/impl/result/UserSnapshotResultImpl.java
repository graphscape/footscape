/**
 *  Nov 28, 2012
 */
package com.fs.expector.dataservice.impl.result;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.support.ResultSupport;
import com.fs.expector.dataservice.api.result.UserSnapshotResultI;

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