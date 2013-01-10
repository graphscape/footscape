/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;

/**
 * @author wu
 * 
 */
public class OpenChatRoomAP extends ActionHandlerSupport {

	public OpenChatRoomAP() {

	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();//
		// this AP service for activity.
		// open a chat room for the activity.
		ActivityModelI am = (ActivityModelI) c.getModel();
		GChatControlI gc = ae.getControl().getClient(true)
				.find(GChatControlI.class, true);

		gc.join(am.getActivityId());

	}

}
