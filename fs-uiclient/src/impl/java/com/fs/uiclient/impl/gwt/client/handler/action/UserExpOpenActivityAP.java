/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpOpenActivityAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public UserExpOpenActivityAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		String actId = (String) ae.getProperty("actId", true);
		ActivitiesControlI ac = getControl(ActivitiesControlI.class, true);//

		ac.refresh(actId);

	}

}
