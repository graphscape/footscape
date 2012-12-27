/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.cooper;

import java.util.List;

import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.operations.CooperConfirmOperationI;
import com.fs.expector.dataservice.api.operations.CooperRequestOperationI;
import com.fs.expector.dataservice.api.result.CooperConfirmResultI;
import com.fs.expector.dataservice.api.result.CooperRequestResultI;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;

/**
 * @author wu
 * 
 */
public class CooperHandler extends ExpectorTMREHSupport {
	// query activities by account.

	@Handle("request")
	public void handleRequest(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.
		CooperRequestOperationI ro = this.dataService
				.prepareOperation(CooperRequestOperationI.class);
		String expId1 = (String) req.getPayload("expId1", true);
		String expId2 = (String) req.getPayload("expId2", true);

		ro.sessionId(this.getSessionId(hc));
		ro.expId1(expId1);
		ro.expId2(expId2);

		CooperRequestResultI rst = ro.execute().getResult().assertNoError();
		String cid = rst.getCooperRequestId();

		res.setPayload("cooperRequestId", cid);//
	}

	@Handle("confirm")
	public void handleConfirm(HandleContextI hc, RequestI req, ResponseI res) {
		String crid = (String) req.getPayload("cooperRequestId", true);
		boolean findAct = req.getPayload(Boolean.class, "useNewestActivityId",
				Boolean.FALSE);//
		CooperRequest cr = this.dataService.getNewestById(CooperRequest.class,
				crid, true);
		String exp2ActId = null;
		if (findAct) {// find the newest activity for the expId2,in case there
						// is already one activity for it.

			ExpActivity ea = this.dataService.getNewest(ExpActivity.class,
					ExpActivity.PK_EXP_ID, cr.getExpId2(), false);
			if (ea != null) {
				exp2ActId = ea.getActivityId();//

			}
		}

		// the relation between activity and user.
		CooperConfirmOperationI ro = this.dataService
				.prepareOperation(CooperConfirmOperationI.class);

		ro.sessionId(this.getSessionId(hc));
		ro.cooperRequestId(crid);
		ro.createNewActivity(exp2ActId == null);//
		ro.exp2ActivityId(exp2ActId);//
		// create new activity for this cooper,do not
		// track and find the related activity.
		// otherwise,exp2's activityId must be provided,that means the exp1
		// should be bring into the activity of exp2 is in.
		// ro.exp2ActivityId(activityId);

		CooperConfirmResultI rst = ro.execute().getResult().assertNoError();
		String ccid = rst.getCooperConfirmId();

		res.setPayload("cooperConfirmId", ccid);//
	}

	// TODO replace by server notifier to client.
	@Handle("refreshIncomingCr")
	public void handleRefreshIncomingCr(HandleContextI hc, RequestI req,
			ResponseI res) {
		List<String> crid = (List<String>) req.getPayload(
				"cooperRequestIdList", true);
		List<CooperRequest> crL = this.dataService.getNewestListById(
				CooperRequest.class, crid, true, true);
		List<PropertiesI<Object>> ptsL = NodeWrapperUtil.convert(crL);
		res.setPayload("cooperRequestList", ptsL);
	}

}
