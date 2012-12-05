/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.uiserver.impl.handler.usshot;

import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.expapp.operations.UserSnapshotOperationI;
import com.fs.dataservice.api.expapp.wrapper2.UserSnapshot;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class UserSnapshotHandler extends UiHandlerSupport {

	@Handle("snapshot")
	public void handleSnapshot(HandleContextI hc, RequestI req, ResponseI res) {

		// create new user exp
		boolean refresh = req.getPayload(Boolean.class, "refresh",
				Boolean.FALSE);
		String accId = this.getLogin(hc, true).getAccountId();//

		if (refresh) {
			this.snapshot(accId);
		}

		UserSnapshot us = this.getSnapshot(accId, false);
		if (us == null) {
			this.snapshot(accId);
			us = this.getSnapshot(accId, true);
		}

		res.setPayload("expIdList", us.getActivityIdList());
		res.setPayload("activityIdList", us.getExpIdList());
		res.setPayload("cooperRequestIdList", us.getCooperRequestIdList());
	}

	protected UserSnapshot getSnapshot(String accId, boolean force) {
		UserSnapshot rt = this.dataService.getNewest(UserSnapshot.class,
				UserSnapshot.PK_ACCOUNT_ID, accId, force);

		return rt;

	}

	protected void snapshot(String accId) {
		// (String) req.getPayload("accountId");
		UserSnapshotOperationI uso = this.dataService
				.prepareOperation(UserSnapshotOperationI.class);
		uso.accountId(accId);
		VoidResultI rst = uso.execute().getResult().assertNoError();
		//
	}

}
