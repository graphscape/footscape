/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicommons.api.gwt.client.schedule.event;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI.TaskI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class ScheduleEvent extends Event {

	public static final Type<ScheduleEvent> TYPE = new Type<ScheduleEvent>("schedule");

	/**
	 * @param type
	 */
	public ScheduleEvent(TaskI task) {
		super(TYPE, task);
	}

	public TaskI getTask() {
		return (TaskI) this.source;
	}
	
}
