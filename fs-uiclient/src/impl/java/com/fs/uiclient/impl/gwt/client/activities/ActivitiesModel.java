/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import java.util.List;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ActivitiesModel extends ModelSupport implements ActivitiesModelI {

	/**
	 * @param name
	 */
	public ActivitiesModel(String name) {
		super(name);
		ControlUtil.addAction(this, Actions.A_ACTS_ACTIVITIES);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public ItemModel getItem(String actId, boolean force) {
		//
		return this.getChild(ItemModel.class, actId, force);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI#getItemList()
	 */
	@Override
	public List<ItemModel> getItemList() {
		// TODO Auto-generated method stub
		return this.getChildList(ItemModel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI#getActivityList
	 * ()
	 */
	@Override
	public List<ActivityModelI> getActivityList() {
		return this.getChildList(ActivityModelI.class);
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public ActivityModelI getActivity(String actId) {
		//
		return this.getChild(ActivityModelI.class, actId, false);

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void addItem(ItemModel im) {
		//
		im.parent(this);
	}

}
