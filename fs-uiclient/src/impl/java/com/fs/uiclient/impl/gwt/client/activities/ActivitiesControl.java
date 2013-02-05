/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesControlI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityView;
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

		return this.getControl(MainControlI.class, true).getActivitiesModel();

	}

	public ActivityModelI getActivity(String actId, boolean force) {

		ActivityModelI rt = this.getActivitiesModel().getChild(ActivityModelI.class, actId, false);
		if (rt == null) {
			if (force) {
				throw new UiException("no activity with id:" + actId);
			}
			return null;
		}
		return rt;

	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void openActivity(final ActivityModelI am) {
		//
		final String actId = am.getActivityId();
		ActivitiesModelI asm = this.getActivitiesModel();
		ActivityModelI old = this.getActivitiesModel().getActivity(actId);
		if (old != null) {
			old.parent(null);// remove
		}
		asm.child(am);

		ActivityView av = this.getBodyView().getOrCreateItem(Path.valueOf("/activity-view/" + actId),
				new CreaterI<ActivityView>() {

					@Override
					public ActivityView create(ContainerI ct) {
						//
						return new ActivityView(ct, am);
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
	public void refresh() {// refresh index.
		new ActionEvent(this, Actions.A_ACTS_ACTIVITIES).dispatch();
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public ItemModel getActivityItem(String actId, boolean b) {
		//
		return this.getActivitiesModel().getItem(actId, b);
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void addActivityItem(ItemModel im) {
		this.getActivitiesModel().addItem(im);
	}

}
