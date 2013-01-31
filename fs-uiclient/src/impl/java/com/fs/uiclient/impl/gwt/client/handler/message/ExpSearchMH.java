/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ExpSearchMH extends MHSupport {

	/**
	 * @param c
	 */
	public ExpSearchMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		ExpSearchControlI c = this.getControl(ExpSearchControlI.class, true);
		ExpSearchModelI sm = this.getModel(ExpSearchModelI.class, true);

		sm.clean(ExpItemModel.class);// clean items.listen by the view.

		MessageData res = t.getMessage();

		List<ObjectPropertiesData> expL = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"expectations", true);

		for (int i = 0; i < expL.size(); i++) {
			ObjectPropertiesData oi = expL.get(i);
			String expId = (String) oi.getProperty("id");
			String body = (String) oi.getProperty("body");
			DateData timestamp = (DateData) oi.getProperty("timestamp");
			String actId = (String) oi.getProperty("activityId");
			String accId = (String) oi.getProperty("accountId");
			String nick = (String) oi.getProperty("nick");
			String icon = (String) oi.getProperty("iconDataUrl");

			ExpItemModel ei = sm.addExpItem(expId);
			ei.setActivityId(actId == null ? null : actId);
			ei.setTimestamp(timestamp);
			ei.setBody(body);//
			ei.setAccountId(accId);
			ei.setIconDataUrl(icon);
			ei.commit();
		}

	}

}
