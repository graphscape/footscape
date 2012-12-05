/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.activities;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.impl.gwt.client.activity.ActivityModel;
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
		ControlUtil.addAction(this, ActivitiesModelI.A_REFRESH);
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
	 * Oct 21, 2012
	 */
	@Override
	public ItemModel addItem(String actId, String expId) {
		//
		ItemModel rt = new ItemModel(actId, actId, expId);
		this.child(rt);
		return rt;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public ActivityModelI getActivity(String actId) {
		//
		ActivityModelI rt = this.getChildById(actId, false);

		return rt;

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public ActivityModelI addActivity(String actId) {
		ActivityModelI rt = new ActivityModel(actId, actId);
		rt.parent(this);//
		rt.setActivityId(actId);
		return rt;
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

}
