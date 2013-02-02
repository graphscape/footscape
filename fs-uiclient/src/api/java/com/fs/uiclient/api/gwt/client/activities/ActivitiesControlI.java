/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.api.gwt.client.activities;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI.ItemModel;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface ActivitiesControlI extends ControlI {

	public void openActivity(ActivityModelI act);

	public void refresh();
	
	public void refresh(String actId);
	
	public ActivityModelI getActivity(String actId, boolean force);

	public ItemModel getActivityItem(String actId, boolean b);

	public void addActivityItem(ItemModel im);

}
