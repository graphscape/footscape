/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class UserExpModel extends ModelSupport {

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityId");

	public static final Location L_EXPID = Location.valueOf("expId");

	public static final Location L_BODY = Location.valueOf("body");//

	public static final Location L_TIMESTAMP = Location.valueOf("timestamp");//

	public static final Location L_ISSELECTED = Location.valueOf("isSelected");//

	public static final Location L_ISEXPANDED = Location.valueOf("isExpanded");//

	private String cooperReqId;
	
	public static final String A_OPEN_ACTIVITY = "activity";
	
	public static final String A_SELECT = "select";
	
	

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
		this.setValue(L_ISSELECTED, sel);
	}

	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

	public boolean isSelected() {
		return this.getValue(Boolean.class, L_ISSELECTED, Boolean.FALSE);
	}

	public void setActivityId(String actId) {
		this.setValue(L_ACTIVITY_ID, actId);
	}

	public String getActivityId(boolean force) {
		String rt = this.getValue(String.class, L_ACTIVITY_ID);
		if (rt == null && force) {
			throw new UiException("no activity with exp:" + this.getExpId());
		}
		return rt;

	}
	public void setTimestamp(DateData ts){
		this.setValue(L_TIMESTAMP, ts);
	}
	
	public DateData getTimestamp(boolean force) {
		return (DateData) this.getValue(L_TIMESTAMP, force);
	}

	/**
	 *Dec 4, 2012
	 */
	public void setCooperReqId(String string) {
		this.cooperReqId = string;
	}

	/**
	 * @return the cooperReqId
	 */
	public String getCooperReqId() {
		return cooperReqId;
	}
}
