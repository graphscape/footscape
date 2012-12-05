/**
 * 
 */
package com.fs.uiclient.impl.gwt.client.tasks;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;

/**
 * @author wuzhen
 * 
 */
public class ActivitiesRefreshHandler implements HandlerI<ScheduleEvent> {

	@Override
	public void handle(ScheduleEvent e) {
		UiClientI client = e.getSource().getContainer()
				.get(UiClientI.class, true);

		ActivitiesModelI am = client.getRootModel().find(
				ActivitiesModelI.class, true);
		ControlUtil.triggerAction(am, ActivitiesModelI.A_REFRESH);

	}

}
