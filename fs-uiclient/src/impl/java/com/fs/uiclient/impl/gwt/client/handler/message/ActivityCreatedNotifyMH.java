/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ActivityCreatedNotifyMH extends NotifyMH {

	/**
	 * @param c
	 */
	public ActivityCreatedNotifyMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		ActivitiesControlI cc = this.getControl(ActivitiesControlI.class,
				true);
		cc.refresh(null);

	}

}
