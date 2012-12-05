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
	public static final String A_REFRESH = "refresh";

	public static class ItemModel extends ModelSupport {

		public static final Location L_ACTID = Location.valueOf("actId");

		// the related exp id for this user.
		public static final Location L_USER_EXP_ID = Location
				.valueOf("userExpId");

		/**
		 * @param name
		 * @param actId
		 */
		public ItemModel(String name, String actId, String exp) {
			super(name);
			this.setValue(L_ACTID, actId);
			this.setValue(L_USER_EXP_ID, exp);
		}

		public String getActId() {
			return this.getValue(String.class, L_ACTID);
		}

		public String getUserExpId() {
			return this.getValue(String.class, L_USER_EXP_ID);
		}

	}

	public List<ItemModel> getItemList();
	
	public List<ActivityModelI> getActivityList();
	
	public ItemModel getItem(String actId, boolean force);

	public ItemModel addItem(String actId, String expId);

	public ActivityModelI getActivity(String actId);

	public ActivityModelI addActivity(String actId);

}
