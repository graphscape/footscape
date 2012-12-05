/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.chatrooms;

import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsModelI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;

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

		ChatRoomsModelI csm = (ChatRoomsModelI) c.getModel();
		RoomsModelI rsm = c.getClient(true).getRootModel()
				.find(RoomsModelI.class, true);

		String actId = csm.getValue(String.class,ChatRoomsModelI.L_ACTIVITY_ID);
		
		String rname = ChatRoomsControl.convertActivityId2RoomName(actId);//convert to room name

		rsm.setValue(RoomsModelI.L_ROOMNAME_EDITING, rname);
		// open the view of the room in the right of the frwk.
		rsm.setManagerForNewRoom(BossModelI.M_RIGHT);
		// TODO add method RoomsModelI.addRoom();
		//listen to the room 
		
		// and join

		ControlUtil.triggerAction(rsm, RoomsModelI.A_JOIN);

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
