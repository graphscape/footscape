/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import java.util.ArrayList;
import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
import com.fs.uiclient.api.gwt.client.event.ActivityCreatedEvent;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotControlI;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class ActivitiesAP extends ActionHandlerSupport {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		
		UserSnapshotModelI mc = c.getManager()
				.getControl(UserSnapshotControlI.class, true).getModel();
		List<String> actIdL = mc.getActivityIdList();
		MsgWrapper req = this.newRequest(Path.valueOf("/activities/activities"));
		
		req.setPayload("idList", ListDataUtil.toStringDataList(actIdL));//
		this.sendMessage(ae, req);
	}

	/*
	 * Oct 21, 2012
	 */
	

}
