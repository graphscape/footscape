/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.schedule;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI;
import com.fs.uicommons.api.gwt.client.schedule.SchedulerI.TaskI;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;

/**
 * @author wu
 * 
 */
public class SchedulerTest extends TestBase {

	private int count = 5;

	private int intervalMs = 1 * 1000;

	public void testScheduler() {
		SchedulerI sc = this.container.get(SchedulerI.class, true);
		sc.scheduleRepeat("test", this.intervalMs,
				new HandlerI<ScheduleEvent>() {

					@Override
					public void handle(ScheduleEvent e) {
						SchedulerTest.this.onScheduleEvent(e);
					}
				});
		this.delayTestFinish((this.count + 1) * intervalMs);
	}

	/**
	 * Oct 21, 2012
	 */
	protected void onScheduleEvent(ScheduleEvent e) {
		count--;
		System.out.println("scheudler event recevied:" + e + ",remains:"
				+ count);
		if (count < 0) {
			TaskI task = e.getTask();
			SchedulerI s = (SchedulerI)e.getTask().getParent();
			
			task.cancel();
			
			this.finishTest();
			
		}
	}
}
