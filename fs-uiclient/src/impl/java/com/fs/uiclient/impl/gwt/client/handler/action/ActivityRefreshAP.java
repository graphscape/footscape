/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class ActivityRefreshAP extends ActionHandlerSupport {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {

		MsgWrapper req = this.newRequest(Path.valueOf("/activity/refresh"));

		String actId = ((ActivityModelI) ae.getControl().getModel()).getActivityId();

		req.getPayloads().setProperty("activityId", StringData.valueOf(actId));
		this.sendMessage(ae, req);
	}

}
