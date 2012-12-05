/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu 
 */
public class SelectExpAP implements ActionProcessorI {

	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		MainModelI fm = c.getModel().getTopObject()
				.find(MainModelI.class, true);
		// TODO create only when required.
		UserExpModel eem = fm.getChild(UserExpModel.class, true);
		eem.setValue(UserExpModel.L_ISSELECTED, true);// listen by the view of
														// the
														// model.
	}

	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {

	}

}
