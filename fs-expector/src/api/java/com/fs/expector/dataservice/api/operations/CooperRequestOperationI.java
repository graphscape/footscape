/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.operations;

import com.fs.expector.dataservice.api.result.CooperRequestResultI;

/**
 * @author wu
 * 
 */
public interface CooperRequestOperationI extends
		AuthedOperationI<CooperRequestOperationI, CooperRequestResultI> {
	public static final String EXPID1 = "expId1";

	public static final String EXPID2 = "expId2";

	public CooperRequestOperationI expId1(String expUid);//

	public CooperRequestOperationI expId2(String expUid);//

}
