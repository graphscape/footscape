/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityModel;
import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ActivitiesControl extends ControlSupport implements ActivitiesControlI {

	/**
	 * @param name
	 */
	public ActivitiesControl(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		//
		super.processChildModelAdd(p, cm);

	}

	public ActivitiesModelI getActivitiesModel() {
		return this.getRootModel().find(ActivitiesModelI.class, true);
	}

	public ActivityModelI getActivity(String actId, boolean force) {

		String lname = this.activityModelName(actId);
		ActivityModelI rt = this.getActivitiesModel().getChild(ActivityModelI.class, lname, false);
		if (rt == null) {
			if (force) {
				throw new UiException("no activity with id:" + actId);
			}
			return null;
		}
		return rt;

	}

	private String activityModelName(String actId) {
		String rt = "activity-" + actId;
		return rt;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void openActivity(final String actId) {
		//

		ActivityModelI am = this.getOrCreateModel(this.getActivitiesModel(), ActivityModelI.class, actId,
				new CreaterI<ActivityModelI>() {

					@Override
					public ActivityModelI create(ContainerI ct) {
						//
						return new ActivityModel(actId, actId);
					}
				});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI#refresh(
	 * java.lang.String)
	 */
	@Override
	public void refresh(String actId) {

		MsgWrapper req = this.newRequest(Path.valueOf("/activity/refresh"));
		req.setPayload("actId", actId);
		this.sendMessage(req);

	}

	@Override
	public void refresh() {
		new ActionEvent(this, Actions.A_ACTS_ACTIVITIES).dispatch();
	}

}
