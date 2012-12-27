/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import java.util.ArrayList;
import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
import com.fs.uiclient.api.gwt.client.event.ActivityCreatedEvent;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotControlI;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class ActivitiesAP implements ActionProcessorI {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		UserSnapshotModelI mc = c.getManager()
				.getControl(UserSnapshotControlI.class, true).getModel();
		List<String> actIdL = mc.getActivityIdList();
		req.setPayload("idList", ListDataUtil.toStringDataList(actIdL));//

	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		if (res.getErrorInfos().hasError()) {
			return;// ignore when error
		}
		// TODO provide a general way for this.
		ActivitiesModelI asm = (ActivitiesModel) c.getModel();
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res
				.getPayloads().getProperty("activities");

		for (int i = 0; i < ld.size(); i++) {

			// TODO general way,converter from ObjectPropertiesData to Model.

			ObjectPropertiesData oi = ld.get(i);
			StringData actIdD = (StringData) oi.getProperty("id");
			String actId = actIdD.getValue();
			List<String> expL = this
					.getExpIdList((ListData<ObjectPropertiesData>) oi
							.getProperty("expectations"));
			ItemModel im = asm.getItem(actId, false);
			if (im == null) {
				im = new ItemModel(actId, expL);//
				new ActivityCreatedEvent((ActivitiesControlI) c, actId);
			}
			// TODO other properties?

		}
	}

	protected List<String> getExpIdList(ListData<ObjectPropertiesData> ptsL) {// TODO
																				// move
																				// to
																				// Util.
		List<String> rt = new ArrayList<String>();
		for (int i = 0; i < ptsL.size(); i++) {
			ObjectPropertiesData pts = ptsL.get(i);
			StringData e = (StringData) pts.getProperty("expId");
			// StringData e = (StringData) pts.getProperty("accountId");
			// StringData e = (StringData) pts.getProperty("body");

			rt.add(e.getValue());

		}
		return rt;
	}

}
