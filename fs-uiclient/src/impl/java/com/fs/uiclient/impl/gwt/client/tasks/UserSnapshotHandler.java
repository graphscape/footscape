/**
 * 
 */
package com.fs.uiclient.impl.gwt.client.tasks;

import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.schedule.event.ScheduleEvent;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;

/**
 * @author wuzhen
 * 
 */
public class UserSnapshotHandler implements HandlerI<ScheduleEvent> {

	@Override
	public void handle(ScheduleEvent e) {
		UiClientI client = e.getSource().getContainer()
				.get(UiClientI.class, true);

		UserSnapshotModelI am = client.getRootModel().find(
				UserSnapshotModelI.class, true);
		ControlUtil.triggerAction(am, UserSnapshotModelI.A_SNAPSHOT);//

	}

}
