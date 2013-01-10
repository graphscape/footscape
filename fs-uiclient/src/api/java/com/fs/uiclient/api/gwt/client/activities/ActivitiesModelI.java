/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.api.gwt.client.activities;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 *         <p>
 *         The manager for activities for the session user.
 *         <p>
 *         It will repeatedly refresh for the activity state changing from
 *         server side.such as new activity created,participant exiting event.
 */
public interface ActivitiesModelI extends ModelI {
	// refresh the activities list of sumber info,such as activity id.
	// detail infor is not need here.
	public static class ItemModel extends ModelSupport {

		protected String actId;

		protected List<String> expIdList;

		/**
		 * @param name
		 * @param actId
		 */
		public ItemModel(String actId, List<String> expIdList) {
			super(actId);
			this.actId = actId;
			this.expIdList = expIdList;
		}

		public String getActId() {
			return this.actId;
		}

		public List<String> getUserExpId() {
			return this.expIdList;
		}

	}

	public List<ItemModel> getItemList();

	public List<ActivityModelI> getActivityList();

	public ItemModel getItem(String actId, boolean force);

	public ActivityModelI getActivity(String actId);

	public ActivityModelI addActivity(String actId);

}
