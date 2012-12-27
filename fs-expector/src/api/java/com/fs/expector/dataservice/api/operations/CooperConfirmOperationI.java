/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.operations;

import com.fs.expector.dataservice.api.result.CooperConfirmResultI;

/**
 * @author wu
 *         <p>
 *         TODO operation error should be properly recorded and
 *         processed,otherwise there will be a broken flow of processing.
 */
public interface CooperConfirmOperationI extends
		AuthedOperationI<CooperConfirmOperationI, CooperConfirmResultI> {

	public static final String PK_COOPER_REQUEST_ID = "cooperRequestId";

	public static final String PK_CREATE_NEW_ACTIVITY = "createNewActivity";

	public static final String PK_EXP2_ACTIVITY_UID = "exp2ActivityUid";

	public CooperConfirmOperationI cooperRequestId(String cooperRequestUid);//

	// the activity of the exp2 is in,may null for a new activity to be created.
	public CooperConfirmOperationI exp2ActivityId(String activityId);

	public CooperConfirmOperationI createNewActivity(boolean cna);

}
