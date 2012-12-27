/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.tasks;

import com.fs.uicommons.api.gwt.client.schedule.SchedulerI;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
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
		// listener to the authed event,trigger activity's query repeatedly
		SessionModelI sm = this.getClient(true).getRootModel()
				.find(SessionModelI.class, true);

		// TODO this may by missed,if here the authed event already raised.

		sm.addValueHandler(SessionModelI.L_IS_AUTHED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						TaskManager.this.onAuthedEvent(e);
					}
				});

	}

	/**
	 * Oct 21, 2012
	 */
	protected void onAuthedEvent(ModelValueEvent e) {
		// start the scheduler
		String secS = this.getClient(true).getParameter("refresh.interval",
				"60S");
		if (secS.endsWith("S")) {
			secS = secS.substring(0, secS.length() - 1);
		} else {
			throw new UiException("TODO:" + secS);
		}
		int intervalMS = Integer.parseInt(secS) * 1000;// ms

		SchedulerI sc = this.getContainer().get(SchedulerI.class, true);
		sc.scheduleRepeat("usshot-refresh", intervalMS,// 2 sec
				new UserSnapshotHandler());
		sc.scheduleRepeat("activity-refresh", intervalMS,// 2 sec
				new ActivityRefreshHandler());

	}

}
