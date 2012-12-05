/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.rooms;

import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsControlI;

/**
 * @author wu
 * 
 */
public class JoinAP implements ActionProcessorI {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		//
		RoomsControlI rmc = (RoomsControlI) c;
		rmc.join();//
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
