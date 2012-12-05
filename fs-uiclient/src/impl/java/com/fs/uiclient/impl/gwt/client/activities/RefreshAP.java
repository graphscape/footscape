/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
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

	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		if (res.getErrorInfos().hasError()) {
			return;// ignore when error
		}

		ActivitiesModelI asm = (ActivitiesModel) c.getModel();
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res
				.getPayloads().getProperty("activities");
		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			StringData actIdD = (StringData) oi.getProperty("actId");
			String actId = actIdD.getValue();
			
			StringData expIdD = (StringData) oi.getProperty("expId");
			String expId = expIdD.getValue();
			
			ItemModel im = asm.getItem(actId, false);
			if (im == null) {
				im = asm.addItem(actId,expId);
			}
			// TODO other properties?

		}
	}

}
