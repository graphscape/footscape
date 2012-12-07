/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 7, 2012
 */
package com.fs.uiclient.impl.gwt.client.usshot;

import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.APSupport;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class SnapshotAP extends APSupport {

	/*
	 * Dec 7, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		req.setPayload("refresh", BooleanData.valueOf(true));

	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	protected void processResponseSuccess(ControlI c, String a, UiResponse res) {

		ListData<StringData> actIdL = (ListData<StringData>) res.getPayload(
				"activityIdList", true);
		ListData<StringData> expIdL = (ListData<StringData>) res.getPayload(
				"expIdList", true);
		ListData<StringData> crIdL = (ListData<StringData>) res.getPayload(
				"cooperRequestIdList", true);
		UserSnapshotModelI usm = c.getModel();
		usm.setActivityIdList(ListDataUtil.toStringList(actIdL));
		usm.setCooperRequestIdList(ListDataUtil.toStringList(crIdL));
		usm.setExpIdList(ListDataUtil.toStringList(expIdL));
		usm.commit();
	}

}
