/**
 * 
 */
package com.fs.uicommons.api.gwt.client.schedule.support;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI.TaskI;
import com.fs.uicommons.api.gwt.client.schedule.TaskAwareI;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public abstract class TaskAwareHandlerSupport implements
		EventHandlerI<ScheduleEvent>, TaskAwareI {
	protected TaskI task;

	@Override
	public void setTask(TaskI task) {
		this.task = task;
	}

}
