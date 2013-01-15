/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.activity;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ActivityHandler extends ExpectorTMREHSupport {

	@Handle("refresh")
	public void handleRefresh(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {

		// the relation between activity and user.
		MessageI req = ew.getMessage();//
		NodeQueryOperationI<ExpActivity> qo = this.dataService.prepareNodeQuery(ExpActivity.TYPE);//

		String actId = (String) req.getPayload("actId", true);

		qo.propertyEq(ExpActivity.PK_ACTIVITY_ID, actId);

		NodeQueryResultI<ExpActivity> rst = qo.execute().getResult().assertNoError();
		// TODO pager

		List<PropertiesI> uaRL = new ArrayList<PropertiesI>();
		List<ExpActivity> uaL = rst.list();
		for (ExpActivity ua : uaL) {
			PropertiesI pts = new MapProperties();
			pts.setProperty("accountId", ua.getPropertyAsString(ExpActivity.PK_ACCOUNT_ID));
			pts.setProperty("expId", ua.getPropertyAsString(ExpActivity.PK_EXP_ID));

			// pts.setProperty("actId",
			// ua.getPropertyAsString(ExpActivity.PK_ACTIVITY_UID));

			uaRL.add(pts);

		}
		res.setPayload("participants", uaRL);
		res.setPayload("actId", actId);
	}
}
