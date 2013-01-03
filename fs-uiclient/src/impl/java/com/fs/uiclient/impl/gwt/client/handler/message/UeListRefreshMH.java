/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.NodeFields;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListModel;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class UeListRefreshMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res.getPayloads().getProperty(
				"userExpList");

		UserExpListModelI elm = this.getModel(t, UserExpListModel.class, true);

		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			StringData expId = (StringData) oi.getProperty(NodeFields.PK_ID, true);
			StringData body = (StringData) oi.getProperty("body", true);
			StringData actId = (StringData) oi.getProperty("activityId", false);//
			ListData<ObjectPropertiesData> crL = (ListData<ObjectPropertiesData>) oi.getProperty(
					"cooperRequestList", true);

			DateData dd = (DateData) oi.getProperty(NodeFields.PK_TIMESTAMP, true);

			UserExpModel uem = elm.getOrAddUserExp(expId.getValue());

			uem.setBody(body.getValue());//
			uem.setTimestamp(dd);
			uem.setActivityId(actId == null ? null : actId.getValue());
			uem.commit();//
		}
	}

}
