/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class RefreshAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		//
		UserExpListModel uelm = c.getModel().cast();
		Long lts = uelm.getLastTimestamp(false);
		req.setPayload("lastTimestamp", DateData.valueOf(lts));// fresh from
																// here
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		if (res.getErrorInfos().hasError()) {
			return;// Do nothing
		}
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res
				.getPayloads().getProperty("userExpList");

		UserExpListModelI elm = (UserExpListModel) c.getModel();

		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			StringData expId = (StringData) oi.getProperty("id", true);
			StringData body = (StringData) oi.getProperty("body", true);
			StringData actId = (StringData) oi.getProperty("activityId", false);//
			ListData<ObjectPropertiesData> crL = (ListData<ObjectPropertiesData>)oi.getProperty("cooperRequestList", true);
			
			DateData dd = (DateData) oi.getProperty("timestamp",true);
			
			UserExpModel uem = elm.getOrAddUserExp(expId.getValue());

			uem.setBody(body.getValue());//
			uem.setTimestamp(dd);
			uem.setActivityId(actId == null?null:actId.getValue());
			uem.commit();//
		}
	}
	

}
