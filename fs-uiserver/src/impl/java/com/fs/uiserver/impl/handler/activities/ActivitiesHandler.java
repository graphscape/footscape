/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.handler.activities;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.wrapper2.UserActivity;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class ActivitiesHandler extends UiHandlerSupport {

	// query activities by account.
	@Handle("refresh")
	public void handleRefresh(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.

		NodeQueryOperationI<UserActivity> qo = this.dataService
				.prepareNodeQuery(UserActivity.TYPE);//
		qo.propertyEq(UserActivity.PK_ACCOUNT_ID, this.getLogin(hc, true)
				.getAccountId());//

		NodeQueryResultI<UserActivity> rst = qo.execute().getResult()
				.assertNoError();
		// TODO pager
		List<PropertiesI<Object>> uaRL = new ArrayList<PropertiesI<Object>>();
		List<UserActivity> uaL = rst.list();
		for (UserActivity ua : uaL) {
			String actId = (String) ua.getProperty(
					UserActivity.PK_ACTIVITY_ID, true);
			String accId = (String) ua.getProperty(UserActivity.PK_ACCOUNT_ID,
					true);

			PropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty("accountId", accId);
			pts.setProperty("actId", actId);

			uaRL.add(pts);

		}
		res.setPayload("activities", uaRL);
	}
}
