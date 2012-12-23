/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicommons.impl.gwt.client.schedule;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.core.client.Scheduler;

/**
 * @author wu
 * 
 */
public class SchedulerImpl extends UiObjectSupport implements SchedulerI,
		ContainerI.AwareI {

	private ContainerI container;

	/*
	 * Oct 21, 2012
	 */
	@Override
	public TaskI scheduleRepeat(String name, int intervalMS,
			EventHandlerI<ScheduleEvent> eh) {
		TaskI rt = this.getTask(name, false);
		if (rt == null) {
			TaskImpl rti = new TaskImpl(name, intervalMS);
			rti.parent(this);//
			Scheduler.get().scheduleFixedPeriod(rti, rti.getIntervalMS());
			rt = rti;
		}
		rt.addHandler(ScheduleEvent.TYPE, eh);

		return rt;

	}

	public TaskI getTask(String name, boolean force) {
		return this.getChild(TaskI.class, name, force);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setContainer(ContainerI c) {
		//
		this.container = c;
	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}
}
