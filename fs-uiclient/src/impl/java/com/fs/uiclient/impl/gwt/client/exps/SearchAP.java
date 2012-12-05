/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class SearchAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		//
		ExpSearchModelI sm = (ExpSearchModelI) c.getModel();
		String expId = sm.getExpId(true);
		
		int pg = sm.getPageNumber();
		
		req.setPayload("pageNumber", IntegerData.valueOf(pg));
		req.setPayload("pageSize", IntegerData.valueOf(sm.getPageSize()));
		
		// the selected expId for matching.
		req.getPayloads().setProperty("expId", StringData.valueOf(expId));

		// user input keywords.
		req.getPayloads().setProperty("keywords", StringData.valueOf(""));

		// TODO keywords

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//
		if (res.getErrorInfos().hasError()) {
			return;// ignore when error
		}
		ExpSearchModelI sm = (ExpSearchModelI) c.getModel();
		
		sm.clean(ExpItemModel.class);//clean items.listen by the view.
		
		ListData<ObjectPropertiesData> expL = (ListData<ObjectPropertiesData>) res
				.getPayloads().getProperty("expectations", true);
		
		for (int i = 0; i < expL.size(); i++) {
			ObjectPropertiesData oi = expL.get(i);
			StringData expId = (StringData) oi.getProperty("id");
			StringData body = (StringData) oi.getProperty("body");
			DateData timestamp = (DateData) oi.getProperty("timestamp");
			StringData actId = (StringData) oi.getProperty("activityId");
			StringData accId = (StringData) oi.getProperty("accountId");
			StringData nick = (StringData) oi.getProperty("nick");		
			StringData icon = (StringData) oi.getProperty("iconDataUrl");

			
			ExpItemModel ei = sm.addExpItem(expId.getValue());
			ei.setActivityId(actId == null ? null : actId.getValue());
			ei.setTimestamp(timestamp);
			ei.setBody(body.getValue());//
			ei.setAccountId(accId.getValue());
			ei.setIconDataUrl(icon.getValue());
			ei.commit();
		}

	}

}
