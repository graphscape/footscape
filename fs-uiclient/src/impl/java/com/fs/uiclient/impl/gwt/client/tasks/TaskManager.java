/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.tasks;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 *         <p>
 * @deprecated by server side message event.
 */
public class TaskManager extends UiObjectSupport {

	/*
	 * Oct 21, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	/**
	 * Oct 21, 2012
	 */
	protected void onAuthedEvent(ModelValueEvent e) {
		// start the scheduler
		String secS = this.getClient(true).getParameter("refresh.interval", "60S");
		if (secS.endsWith("S")) {
			secS = secS.substring(0, secS.length() - 1);
		} else {
			throw new UiException("TODO:" + secS);
		}
		int intervalMS = Integer.parseInt(secS) * 1000;// ms

		SchedulerI sc = this.getContainer().get(SchedulerI.class, true);

		sc.scheduleRepeat("activity-refresh", intervalMS,// 2 sec
				new ActivityRefreshHandler());

	}

}
