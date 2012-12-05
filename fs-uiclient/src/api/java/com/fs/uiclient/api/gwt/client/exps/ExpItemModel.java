/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ExpItemModel extends ModelSupport {

	public static final Location L_EXP_ID = Location.valueOf("expId");

	public static final Location L_EXP_BODY = Location.valueOf("expBody");

	public static final Location L_TIMESTAMP = Location.valueOf("timestamp");

	public static final Location L_ACTIVITY_ID = Location.valueOf("activityId");

	public static final Location L_ACCOUNT_ID = Location.valueOf("accountId");

	public static final Location L_ICONDATA = Location.valueOf("iconDataUrl");

	public static final Location L_NICK = Location.valueOf("nick");

	public static final String A_COPER = "coper";// request coper,set value to
													// CoperModelI, trigger the
													// CoperModelI.coper action
													// then.

	/**
	 * @param name
	 */
	public ExpItemModel(String id) {
		super(id);
		ControlUtil.addAction(this, A_COPER);//
		this.setValue(L_EXP_ID, id);//
	}

	public String getExpId() {
		return (String) this.getValue(L_EXP_ID);
	}

	public String getExpBody() {
		return (String) this.getValue(L_EXP_BODY);
	}

	/**
	 * @param expId2
	 * @return
	 */
	public boolean isExp(String expId2) {

		return ObjectUtil.nullSafeEquals(this.getExpId(), expId2);

	}

	/**
	 * Oct 20, 2012
	 */
	public void setBody(String body) {
		this.setValue(L_EXP_BODY, body);
	}

	public void setActivityId(String value) {
		this.setValue(L_ACTIVITY_ID, value);
	}

	public String getActivityId() {
		return this.getValue(String.class, L_ACTIVITY_ID);
	}

	public void setTimestamp(DateData dd) {
		this.setValue(L_TIMESTAMP, dd);
	}

	public DateData getTimestamp() {
		return (DateData) this.getValue(L_TIMESTAMP);
	}

	public String getIconDataUrl() {
		return (String) this.getValue(L_ICONDATA);

	}

	public void setIconDataUrl(String value) {
		this.setValue(L_ICONDATA, value);
	}

	public void setAccountId(String value) {
		this.setValue(L_ACCOUNT_ID, value);
	}

	public String getAccountId() {
		return this.getValue(String.class, L_ACCOUNT_ID);
	}

	public void setNick(String value) {
		this.setValue(L_NICK, value);
	}

	public String getNick() {
		return this.getValue(String.class, L_NICK);
	}

}
