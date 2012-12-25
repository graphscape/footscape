/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
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
public class RefreshAP implements ActionProcessorI {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		
		String actId = ((ActivityModelI) c.getModel()).getActivityId();
		
		req.getPayloads().setProperty("activityId", StringData.valueOf(actId));

	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		if (res.getErrorInfos().hasError()) {
			return;// ignore when error
		}

		ActivityModelI asm = (ActivityModelI) c.getModel();
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res
				.getPayloads().getProperty("participants");
		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			StringData accIdD = (StringData) oi.getProperty("accountId");
			String accId = accIdD.getValue();

			StringData expIdD = (StringData) oi.getProperty("expId");
			String expId = expIdD.getValue();

			PartnerModel pm = asm.getParticipantByExpId(expId);

			if (pm == null) {// only refresh when not added yet.
				pm = asm.addParticipant(expId, accId);
			}
			// TODO other properties?

		}
	}

}
