/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicore.api.gwt.client.scheduler;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.ScheduleEvent;

/**
 * @author wu
 * 
 */
public interface SchedulerI extends UiObjectI {
	public void scheduleRepeat(String name, int intervelMS);
			
	public void scheduleRepeat(String name, int intervelMS, EventHandlerI<ScheduleEvent> eh);
	
	public void scheduleTimer(int timeout, HandlerI<Object> eh);
	
	public void scheduleDelay(HandlerI<Object> eh);

	public void cancel(String name);
}
