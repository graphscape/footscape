/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ActivityCreatedNotifyMH extends NotifyMH {

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void handle(MsgWrapper t) {
		ActivitiesControlI cc = this.getControl(t, ActivitiesControlI.class,
				true);
		cc.refresh(null);

	}

}
