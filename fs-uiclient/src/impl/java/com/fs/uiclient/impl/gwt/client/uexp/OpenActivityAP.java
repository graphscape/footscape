/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionProcessorI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class OpenActivityAP implements ActionProcessorI {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processRequest(ControlI c, String a, UiRequest req) {
		UserExpModel uem = (UserExpModel) c.getModel();

		String actId = uem.getActivityId();
		if (actId == null) {
			throw new UiException("no activity id for exp:" + uem.getExpId());
		}
		ActivitiesControlI ac = c.getManager().getControl(
				ActivitiesControlI.class, true);//
		ac.openActivity(actId);

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processResponse(ControlI c, String a, UiResponse res) {
		//

	}

}
