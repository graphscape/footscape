/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class ActivityCreatedEvent extends Event {

	public static final Type<ActivityCreatedEvent> TYPE = new Type<ActivityCreatedEvent>("unknow");

	private String activityId;

	/**
	 * @param type
	 */
	public ActivityCreatedEvent(ActivitiesControlI src, String actId) {
		super(TYPE, src);
		this.activityId = actId;
	}

	public String getActivityId() {
		return activityId;
	}

}
