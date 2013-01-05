/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.IncomingCrModel;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.impl.gwt.client.NodeFields;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class IncomingCrRefreshMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		CooperControlI c= this.getControl(t, CooperControlI.class, true);
		CooperModelI cm = c.getModel();
		MessageData res = t.getMessage();
		ListData<ObjectPropertiesData> crL = (ListData<ObjectPropertiesData>) res
				.getPayload("cooperRequestList", true);

		for (int i = 0; i < crL.size(); i++) {
			ObjectPropertiesData od = crL.get(i);
			StringData crId = (StringData) od.getProperty(NodeFields.PK_ID);
			StringData expId1 = (StringData) od.getProperty("expId1");
			StringData expId2 = (StringData) od.getProperty("expId2");
			StringData accountId1 = (StringData) od.getProperty("accountId1");// NOTE
																				// TODO
																				// 1
			StringData accountId2 = (StringData) od.getProperty("accountId2");

			IncomingCrModel crm = new IncomingCrModel(crId.getValue());
			crm.setAccountId1(accountId1.getValue());
			crm.setAccountId2(accountId2.getValue());
			crm.setExpId1(expId1.getValue());
			crm.setExpId2(expId2.getValue());
			crm.commit();
			cm.incomingCr(crm);
			this.onIncomingCr(t,crm);
		}
	}

	/**
	 *Jan 4, 2013
	 */
	private void onIncomingCr(EndpointMessageEvent t, IncomingCrModel crm) {
		UserExpListControlI c = this.getControl(t,UserExpListControlI.class, true);
		c.incomingCr(crm);
	}
	
	

}