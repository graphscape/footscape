/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import java.util.ArrayList;
import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ActivitiesRefreshMH extends MHSupport {

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		// TODO provide a general way for this.
		ActivitiesModelI asm = this.getModel(t, ActivitiesModelI.class, true);
		MessageData res = t.getMessage();
		ListData<ObjectPropertiesData> ld = (ListData<ObjectPropertiesData>) res.getPayloads().getProperty(
				"activities");
		ActivitiesControlI c = this.getControl(t, ActivitiesControlI.class, true);
		for (int i = 0; i < ld.size(); i++) {

			// TODO general way,converter from ObjectPropertiesData to Model.

			ObjectPropertiesData oi = ld.get(i);
			StringData actIdD = (StringData) oi.getProperty("id");
			String actId = actIdD.getValue();
			List<String> expL = this.getExpIdList((ListData<ObjectPropertiesData>) oi
					.getProperty("expectations"));
			ItemModel im = asm.getItem(actId, false);
			if (im == null) {
				im = new ItemModel(actId, expL);//
				asm.child(im);
				// new ActivityCreatedEvent((ActivitiesControlI) c,
				// actId).dispatch();
				this.tryLinkActivityToUserExp(t, actId, expL);
			}

		}
	}

	/**
	 * Jan 6, 2013
	 */
	private void tryLinkActivityToUserExp(EndpointMessageEvent t, String actId, List<String> expL) {
		UserExpListControlI uec = this.getControl(t, UserExpListControlI.class, true);
		UserExpListModelI uem = uec.getModel();
		for (String expId : expL) {
			UserExpModel um = uem.getUserExp(expId, false);
			if (um == null) {
				continue;
			}
			um.setActivityId(actId);//
		}
	}

	protected List<String> getExpIdList(ListData<ObjectPropertiesData> ptsL) {// TODO
																				// move
																				// to
																				// Util.
		List<String> rt = new ArrayList<String>();
		for (int i = 0; i < ptsL.size(); i++) {
			ObjectPropertiesData pts = ptsL.get(i);
			StringData e = (StringData) pts.getProperty("expId");
			// StringData e = (StringData) pts.getProperty("accountId");
			// StringData e = (StringData) pts.getProperty("body");

			rt.add(e.getValue());

		}
		return rt;
	}
}
