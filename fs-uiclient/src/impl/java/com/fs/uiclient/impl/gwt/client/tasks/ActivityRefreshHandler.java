/**
 * 
 */
package com.fs.uiclient.impl.gwt.client.tasks;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class ActivityRefreshHandler implements EventHandlerI<ScheduleEvent> {

	@Override
	public void handle(ScheduleEvent e) {
		UiClientI client = e.getSource().getContainer()
				.get(UiClientI.class, true);
		ActivitiesModelI asm = client.getRootModel()
				.find(ActivitiesModelI.class, true);
		List<ActivityModelI> al = asm.getActivityList();
		for (ActivityModelI am : al) {

			ControlUtil.triggerAction(am, ActivityModelI.A_REFRESH);

		}
	}

}
