/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.handler.cooper;

import com.fs.dataservice.api.expapp.operations.CooperConfirmOperationI;
import com.fs.dataservice.api.expapp.operations.CooperRequestOperationI;
import com.fs.dataservice.api.expapp.result.CooperConfirmResultI;
import com.fs.dataservice.api.expapp.result.CooperRequestResultI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class CooperHandler extends UiHandlerSupport {
	// query activities by account.

	@Handle("request")
	public void handleRequest(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.
		CooperRequestOperationI ro = this.dataService
				.prepareOperation(CooperRequestOperationI.class);
		String expId1 = (String) req.getPayload("expId1", true);
		String expId2 = (String) req.getPayload("expId2", true);

		ro.loginId(this.getLoginId(hc));
		ro.expId1(expId1);
		ro.expId2(expId2);

		CooperRequestResultI rst = ro.execute().getResult().assertNoError();
		String cid = rst.getCooperRequestId();

		res.setPayload("cooperRequestId", cid);//
	}

	@Handle("confirm")
	public void handleConfirm(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.
		CooperConfirmOperationI ro = this.dataService
				.prepareOperation(CooperConfirmOperationI.class);
		String crid = (String) req.getPayload("cooperRequestId", true);

		ro.loginId(this.getLoginId(hc));
		ro.cooperRequestId(crid);
		ro.createNewActivity(true);// create new activity for this cooper,do not
									// track and find the related activity.
		// otherwise,exp2's activityId must be provided,that means the exp1
		// should be bring into the activity of exp2 is in.
		// ro.exp2ActivityId(activityId);

		CooperConfirmResultI rst = ro.execute().getResult().assertNoError();
		String ccid = rst.getCooperConfirmId();

		res.setPayload("cooperConfirmId", ccid);//
	}

}
