/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ExpItemModel extends PropertiesData<Object> {

	public ExpItemModel(PropertiesData<Object> pts) {
		super();
		this.setProperties(pts);
	}

	public String getExpId() {
		return (String) this.getProperty("id");
	}

	public String getExpBody() {
		return (String) this.getProperty("body");
	}

	/**
	 * @param expId2
	 * @return
	 */
	public boolean isExp(String expId2) {

		return ObjectUtil.nullSafeEquals(this.getExpId(), expId2);

	}

	public DateData getTimestamp() {
		DateData rt = (DateData) this.getProperty("timestamp");
		return rt;
	}

	public String getIconDataUrl() {
		return (String) this.getProperty("iconDataUrl");

	}

	public String getAccountId() {
		return (String) this.getProperty("accountId");
	}

	public String getNick() {
		return (String) this.getProperty("nick");
	}

}
