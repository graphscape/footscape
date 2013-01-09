/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 *
 */
public class ActivityRefreshMH extends MHSupport {

	/*
	 *Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		
		ActivityModelI asm = this.getModel(t, ActivityModelI.class, true);
		MessageData res = t.getMessage();
		List<ObjectPropertiesData> ld = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"participants");
		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			String accIdD = (String) oi.getProperty("accountId");
			String accId = accIdD;

			String expIdD = (String) oi.getProperty("expId");
			String expId = expIdD;

			PartnerModel pm = asm.getParticipantByExpId(expId);

			if (pm == null) {// only refresh when not added yet.
				pm = asm.addParticipant(expId, accId);
			}
			// TODO other properties?

		}
	}

}
