/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.model.UserExpActivityEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpCrConfirmEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpIncomingCrEvent;
import com.fs.uiclient.api.gwt.client.event.model.UserExpSelectEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class UserExpModel extends ModelSupport {

	public static final Location L_BODY = Location.valueOf("title");//

	public static final Location L_TIMESTAMP = Location.valueOf("timestamp");//

	private String expId;

	private boolean selected;

	/**
	 * @param name
	 */

	public UserExpModel(String id) {
		super(id);

		this.expId = id;
	}

	public void setBody(String body) {
		this.setValue(L_BODY, body);
	}

	public String getBody() {
		return this.getValue(String.class, L_BODY);
	}

	public String getExpId() {
		return this.expId;
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

}
