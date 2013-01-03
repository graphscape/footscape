/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;

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
		AChatModel cm = (AChatModel) c.getModel().getTopObject().find(AChatModel.class, true);
		// this AP service for activity.
		// open a chat room for the activity.
		ActivityModelI am = (ActivityModelI) c.getModel();

		cm.setActivityIdToJoin(am.getActivityId());

		ControlUtil.triggerAction(cm, AChatModel.A_OPEN);//

	}

}
