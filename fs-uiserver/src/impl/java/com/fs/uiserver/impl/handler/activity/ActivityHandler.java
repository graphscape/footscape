/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.handler.activity;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class ActivityHandler extends UiHandlerSupport {

	//
	public void handleDetail(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.

		NodeQueryOperationI<ExpActivity> qo = this.dataService
				.prepareNodeQuery(ExpActivity.TYPE);//

		String actId = (String) req.getPayload("actId", true);

		qo.propertyEq(ExpActivity.PK_ACTIVITY_ID, actId);

		NodeQueryResultI<ExpActivity> rst = qo.execute().getResult().assertNoError();
		// TODO pager

		List<PropertiesI> uaRL = new ArrayList<PropertiesI>();
		List<ExpActivity> uaL = rst.list();
		for (ExpActivity ua : uaL) {
			PropertiesI pts = new MapProperties();
			pts.setProperty("accountId",
					ua.getPropertyAsString(ExpActivity.PK_ACCOUNT_ID));
			pts.setProperty("expId",
					ua.getPropertyAsString(ExpActivity.PK_EXP_ID));

			// pts.setProperty("actId",
			// ua.getPropertyAsString(ExpActivity.PK_ACTIVITY_UID));

			uaRL.add(pts);

		}
		res.setPayload("participants", uaRL);
		res.setPayload("actId", actId);
	}
}
