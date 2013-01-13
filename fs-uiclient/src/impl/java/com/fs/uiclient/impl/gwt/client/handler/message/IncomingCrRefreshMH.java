/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.IncomingCrModel;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.impl.gwt.client.NodeFields;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 *         <p>
 *         Refresh Incoming CR,dispatch them to the UserExpList.
 *         <p>
 *         For the deleted Incoming CR,it should not be here to remove from
 *         uelist, but in another message for calling
 *         UserExpListControlI.incomingCrConfirmed() by CooperConfirmSuccessMH
 */
public class IncomingCrRefreshMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		CooperControlI c = this.getControl(t, CooperControlI.class, true);
		CooperModelI cm = c.getModel();
		MessageData res = t.getMessage();
		List<ObjectPropertiesData> crL = (List<ObjectPropertiesData>) res.getPayload("cooperRequestList",
				true);

		for (int i = 0; i < crL.size(); i++) {
			ObjectPropertiesData od = crL.get(i);
			String crId = (String) od.getProperty(NodeFields.PK_ID);
			String expId1 = (String) od.getProperty("expId1");
			String expId2 = (String) od.getProperty("expId2");
			String accountId1 = (String) od.getProperty("accountId1");// NOTE
																		// TODO
																		// 1
			String accountId2 = (String) od.getProperty("accountId2");

			IncomingCrModel crm = new IncomingCrModel(crId);
			crm.setAccountId1(accountId1);
			crm.setAccountId2(accountId2);
			crm.setExpId1(expId1);
			crm.setExpId2(expId2);
			crm.commit();
			cm.incomingCr(crm);
			this.onIncomingCr(t, crm);
		}
	}

	/**
	 * Jan 4, 2013
	 */
	private void onIncomingCr(EndpointMessageEvent t, IncomingCrModel crm) {
		UserExpListControlI c = this.getControl(t, UserExpListControlI.class, true);
		c.incomingCr(crm);
	}

}
