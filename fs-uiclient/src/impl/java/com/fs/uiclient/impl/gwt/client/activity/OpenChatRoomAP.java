/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class OpenChatRoomAP implements ActionProcessorI {

	public OpenChatRoomAP() {

	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		// DeferredCommand.add(new Command(){
		//
		// @Override
		// public void execute() {
		// //
		//
		// }});
		AChatModel cm = (AChatModel) c.getModel().getTopObject()
				.find(AChatModel.class, true);
		// this AP service for activity.
		// open a chat room for the activity.
		ActivityModelI am = (ActivityModelI) c.getModel();

		cm.setActivityIdToJoin(am.getActivityId());

		ControlUtil.triggerAction(cm, AChatModel.A_OPEN);//

	}

	/**
	 * Oct 22, 2012
	 */

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
