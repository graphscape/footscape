/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.handler.activities;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
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
	@Handle("activities")
	public void handleActivities(HandleContextI hc, RequestI req, ResponseI res) {
		List<String> idL = (List<String>) req.getPayload("idList");

		// TODO parttern of data retrieving.
		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>();

		List<Activity> actL = this.dataService.getNewestListById(
				Activity.class, idL, true, false);

		for (Activity act : actL) {
			PropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty("id", act.getId());
			List<PropertiesI<Object>> expL = this.expList(act.getId());
			pts.setProperty("expectations", expL);
			rt.add(pts);
		}
		res.setPayload("activities");
	}

	protected List<PropertiesI<Object>> expList(String actId) {

		List<ExpActivity> eaL = this.dataService.getListNewestFirst(
				ExpActivity.class, ExpActivity.PK_ACTIVITY_ID, actId, 0,
				Integer.MAX_VALUE);

		return this.toPayload(eaL);
	}

	protected List<PropertiesI<Object>> toPayload(List<ExpActivity> eaL) {
		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>();

		for (ExpActivity ea : eaL) {
			String expId = ea.getExpId();
			Expectation exp = this.dataService.getNewestById(Expectation.class,
					expId, true);

			PropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty("accountId", ea.getAccountId());
			pts.setProperty("expId", ea.getExpId());
			pts.setProperty("body", exp.getBody());

			rt.add(pts);
		}

		return rt;
	}
}
