/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicommons.api.gwt.client.schedule;

import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public interface SchedulerI extends UiObjectI {

	public static interface TaskI extends UiObjectI{

		public String getName();
		
		public void cancel();
		
		public int getIntervalMS();

	}

	public TaskI scheduleRepeat(String name, int intervelMS,
			HandlerI<ScheduleEvent> eh);

}
