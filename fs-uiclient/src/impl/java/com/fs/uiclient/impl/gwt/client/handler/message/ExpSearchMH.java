/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.event.AfterExpSearchEvent;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
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
public class ExpSearchMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		ExpSearchControlI c = this.getControl(t, ExpSearchControlI.class, true);
		ExpSearchModelI sm = this.getModel(t, ExpSearchModelI.class, true);

		sm.clean(ExpItemModel.class);// clean items.listen by the view.

		MessageData res = t.getMessage();

		ListData<ObjectPropertiesData> expL = (ListData<ObjectPropertiesData>) res.getPayloads().getProperty(
				"expectations", true);

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

		new AfterExpSearchEvent((ExpSearchControlI) c, null).dispatch();

	}

}
