/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.expector.dataservice.api.wrapper.Activity;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.dataservice.api.wrapper2.UserActivity;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ActivitiesHandler extends ExpectorTMREHSupport {

	// query activities by account.
	@Handle("activities")
	public void handleActivities(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		List<String> idL = (List<String>) req.getPayload("idList", false);
		if (idL == null) {
			idL = this.getUserActivityIdList(ew);
		}

		// TODO parttern of data retrieving.
		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>();

		List<Activity> actL = this.dataService.getNewestListById(Activity.class, idL, true, false);

		for (Activity act : actL) {
			PropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty("id", act.getId());
			List<PropertiesI<Object>> expL = this.expList(act.getId());
			pts.setProperty("expectations", expL);
			rt.add(pts);
		}
		res.setPayload("activities",rt);
	}

	/**
	 * Dec 30, 2012 TODO other statu table entity.
	 */
	private List<String> getUserActivityIdList(TerminalMsgReceiveEW ew) {
		String accId = this.getSession(ew, true).getAccountId();
		NodeQueryOperationI<UserActivity> op = this.dataService.prepareNodeQuery(UserActivity.class);
		op.propertyEq(UserActivity.PK_ACCOUNT_ID, accId);
		NodeQueryResultI<UserActivity> rst = op.execute().getResult().assertNoError();
		Set<String> rt = new HashSet<String>();
		for (UserActivity ua : rst.list()) {
			String aid = ua.getPropertyAsString(UserActivity.PK_ACTIVITY_ID);
			rt.add(aid);
		}
		return new ArrayList<String>(rt);
	}

	protected List<PropertiesI<Object>> expList(String actId) {

		List<ExpActivity> eaL = this.dataService.getListNewestFirst(ExpActivity.class,
				ExpActivity.PK_ACTIVITY_ID, actId, 0, Integer.MAX_VALUE);

		return this.toPayload(eaL);
	}

	protected List<PropertiesI<Object>> toPayload(List<ExpActivity> eaL) {
		List<PropertiesI<Object>> rt = new ArrayList<PropertiesI<Object>>();

		for (ExpActivity ea : eaL) {
			String expId = ea.getExpId();
			Expectation exp = this.dataService.getNewestById(Expectation.class, expId, true);

			PropertiesI<Object> pts = new MapProperties<Object>();
			pts.setProperty("accountId", ea.getAccountId());
			pts.setProperty("expId", ea.getExpId());
			pts.setProperty("body", exp.getBody());

			rt.add(pts);
		}

		return rt;
	}
}
