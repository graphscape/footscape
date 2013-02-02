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
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ActivitiesRefreshMH extends MHSupport {

	/**
	 * @param c
	 */
	public ActivitiesRefreshMH(ContainerI c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		// TODO provide a general way for this.
		MessageData res = t.getMessage();
		List<ObjectPropertiesData> ld = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"activities");
		ActivitiesControlI c = this.getControl(ActivitiesControlI.class, true);
		for (int i = 0; i < ld.size(); i++) {

			// TODO general way,converter from ObjectPropertiesData to Model.

			ObjectPropertiesData oi = ld.get(i);
			String actIdD = (String) oi.getProperty("id");
			String actId = actIdD;
			List<String> expL = this
					.getExpIdList((List<ObjectPropertiesData>) oi.getProperty("expectations"));
			ItemModel im = c.getActivityItem(actId, false);
			if (im == null) {
				im = new ItemModel(actId, expL);//
				c.addActivityItem(im);
				// new ActivityCreatedEvent((ActivitiesControlI) c,
				// actId).dispatch();
				this.tryLinkActivityToUserExp(t, actId, expL);
			}

		}
	}

	/**
	 * Jan 6, 2013.getValue()
	 */
	private void tryLinkActivityToUserExp(EndpointMessageEvent t, String actId, List<String> expL) {

		MainControlI mc = this.getControl(MainControlI.class, true);

		UserExpListModelI uel = mc.getUeListModel();

		for (String expId : expL) {
			UserExpModel um = uel.getUserExp(expId, false);
			if (um == null) {
				continue;
			}
			um.setActivityId(actId);//
			//view update
			mc.openUeList().update(um);

		}

	}

	protected List<String> getExpIdList(List<ObjectPropertiesData> ptsL) {// TODO
																			// move
																			// to
																			// Util.
		List<String> rt = new ArrayList<String>();
		for (int i = 0; i < ptsL.size(); i++) {
			ObjectPropertiesData pts = ptsL.get(i);
			String e = (String) pts.getProperty("expId");
			// String e = (String) pts.getProperty("accountId");
			// String e = (String) pts.getProperty("body");

			rt.add(e);

		}
		return rt;
	}
}
