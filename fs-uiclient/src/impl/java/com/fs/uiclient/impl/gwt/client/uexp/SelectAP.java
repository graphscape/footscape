/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.event.AfterExpSelectedEvent;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class SelectAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		UserExpModel uem = (UserExpModel) c.getModel();
		uem.select(true);
		
		new AfterExpSelectedEvent(c, uem.getExpId()).dispatch();
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
