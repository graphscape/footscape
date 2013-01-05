/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.event.model.UserExpIncomingCrEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpSelectEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class UserExpModel extends ModelSupport {

	public static final Location L_EXPID = Location.valueOf("expId");

	public static final Location L_BODY = Location.valueOf("body");//

	public static final Location L_TIMESTAMP = Location.valueOf("timestamp");//

	public static final Location L_ISEXPANDED = Location.valueOf("isExpanded");//

	public static final String A_OPEN_ACTIVITY = "activity";

	public static final String A_SELECT = "select";

	private String incomingCrId;

	private String activityId;

	private boolean selected;

	/**
	 * @param name
	 */

	public UserExpModel(String name, String id) {
		super(name);
		ControlUtil.addAction(this, A_SELECT);//
		ControlUtil.addAction(this, A_OPEN_ACTIVITY);//
		this.setValue(L_EXPID, id);//

	}

	public void setBody(String body) {
		this.setValue(L_BODY, body);
	}

	public String getBody() {
		return this.getValue(String.class, L_BODY);
	}

	public String getExpId() {
		return this.getValue(String.class, L_EXPID);
	}

	public void select(boolean sel) {
		this.selected = sel;
		new UserExpSelectEvent(this, sel).dispatch();
	}

	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setTimestamp(DateData ts) {
		this.setValue(L_TIMESTAMP, ts);
	}

	public DateData getTimestamp(boolean force) {
		return (DateData) this.getValue(L_TIMESTAMP, force);
	}

	/**
	 * Dec 4, 2012
	 */
	public void setIncomingCrId(String string) {// TODO list
		this.incomingCrId = string;
		new UserExpIncomingCrEvent(this, this.incomingCrId).dispatch();

	}

	/**
	 * @return the cooperReqId
	 */
	public String getIncomingCrId() {
		return this.incomingCrId;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId
	 *            the activityId to set
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}
