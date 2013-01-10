/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * 
 */
public class OpenActivityAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI)ae.getSource();
		UserExpModel uem = (UserExpModel) c.getModel();

		String actId = uem.getActivityId();
		if (actId == null) {
			throw new UiException("no activity id for exp:" + uem.getExpId());
		}
		ActivitiesControlI ac = c.getManager().getControl(
				ActivitiesControlI.class, true);//
		ac.openActivity(actId);

	}

}
