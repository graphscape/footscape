/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.achat;

import com.fs.uiclient.api.gwt.client.achat.AChatModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class OpenAP implements ActionProcessorI {

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {

		AChatModel csm = (AChatModel) c.getModel();
		GChatModel rsm = c.getClient(true).getRootModel().find(GChatModel.class, true);

		String actId = csm.getValue(String.class, AChatModel.L_ACTIVITY_ID);

		String rname = ((AChatControlImpl) c).convertActivityId2RoomName(actId);// convert
																				// to
																				// room
																				// name
		rsm.setGroupIdToJoin(rname);
		// open the view of the room in the right of the frwk.
		// rsm.setManagerForNewRoom(BossModelI.M_RIGHT);
		// TODO add method RoomsModelI.addRoom();
		// listen to the room

		// and join

		ControlUtil.triggerAction(rsm, GChatModel.A_JOIN);

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
