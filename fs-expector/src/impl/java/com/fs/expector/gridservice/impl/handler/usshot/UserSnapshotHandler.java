/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.expector.gridservice.impl.handler.usshot;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.operations.UserSnapshotOperationI;
import com.fs.expector.dataservice.api.result.UserSnapshotResultI;
import com.fs.expector.dataservice.api.wrapper2.UserSnapshot;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class UserSnapshotHandler extends ExpectorTMREHSupport {

	@Handle("snapshot")
	public void handleSnapshot(TerminalMsgReceiveEW ew, HandleContextI hc, RequestI req, ResponseI res) {

		// create new user exp
		boolean refresh = req.getPayload(Boolean.class, "refresh", Boolean.FALSE);
		String accId = this.getSession(ew, true).getAccountId();//

		if (refresh) {
			this.snapshot(accId);
		}

		UserSnapshot us = this.getSnapshot(accId, false);
		if (us == null) {
			this.snapshot(accId);
			us = this.getSnapshot(accId, true);
		}

		res.setPayload("activityIdList", us.getActivityIdList());
		res.setPayload("expIdList", us.getExpIdList());
		res.setPayload("cooperRequestIdList", us.getCooperRequestIdList());
	}

	protected UserSnapshot getSnapshot(String accId, boolean force) {
		UserSnapshot rt = this.dataService.getNewest(UserSnapshot.class, UserSnapshot.PK_ACCOUNT_ID, accId,
				force);

		return rt;

	}

	protected String snapshot(String accId) {
		// (String) req.getPayload("accountId");
		UserSnapshotOperationI uso = this.dataService.prepareOperation(UserSnapshotOperationI.class);
		uso.accountId(accId);
		uso.refreshForSave(true);
		UserSnapshotResultI rst = uso.execute().getResult().assertNoError();
		return rst.get(true);//
		//
	}

}
