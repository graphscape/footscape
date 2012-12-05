/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicommons.impl.gwt.client.schedule;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI.TaskI;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

/**
 * @author wu
 * 
 */
public class TaskImpl extends UiObjectSupport implements TaskI,
		RepeatingCommand {

	private boolean canceled;

	private String name;

	private int intervalMS;

	/**
	 * @param name
	 */
	public TaskImpl(String name, int itvMS) {
		this.name = name;
		this.intervalMS = itvMS;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void cancel() {
		this.canceled = true;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public boolean execute() {
		//
		new ScheduleEvent(this).dispatch();
		return !this.canceled;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public String getName() {
		//
		return this.name;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public int getIntervalMS() {
		//
		return this.intervalMS;
	}

}
