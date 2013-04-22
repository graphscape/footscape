/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.NodeFields;
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
		return (String) this.getProperty(NodeFields.PK_ID, true);
	}

	public String getExpTitle() {
		return (String) this.getProperty("title");
	}

	public String getExpBodyAsHtml() {
		return getExpBodyAsHtml(this.getExpBody());
	}

	public static String getExpBodyAsHtml(String rt) {
		if (rt == null) {
			return null;
		}
		rt = rt.replace("\n", "<br>");
		return rt;
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
		DateData rt = (DateData) this.getProperty(NodeFields.PK_TIMESTAMP, true);
		return rt;
	}

	public String getIcon() {
		String rt = (String) this.getProperty("icon");
		return "n/a".equals(rt) ? null : rt;

	}

	public String getImage() {

		String rt = (String) this.getProperty("image");
		if (rt.equals("n/a")) {
			rt = null;
		}
		return rt;

	}

	public String getUserIcon() {
		return (String) this.getProperty("userIcon");

	}

	public String getAccountId() {
		return (String) this.getProperty("accountId");
	}

	public String getNick() {
		return (String) this.getProperty("nick");
	}

}
