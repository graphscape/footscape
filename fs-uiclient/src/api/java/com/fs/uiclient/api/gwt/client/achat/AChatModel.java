/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.achat;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class AChatModel extends ModelSupport {
	public static final String PREFIX_ACTIVITY = "ar-";// activity room,UPPER
	// case is not work,

	public static final String A_OPEN = "open";// open chat room for activity.

	private String activityIdToJoin;

	public AChatModel(String name) {
		super(name);

		ControlUtil.addAction(this, AChatModel.A_OPEN);
	}

	/**
	 * 
	 */
	public String getActivityIdToJoin(boolean force) {
		if (this.activityIdToJoin == null && force) {
			throw new UiException("no activityIdToJoin");
		}
		return this.activityIdToJoin;
	}

	public void setActivityIdToJoin(String activityIdToJoin) {
		this.activityIdToJoin = activityIdToJoin;
	}

}
