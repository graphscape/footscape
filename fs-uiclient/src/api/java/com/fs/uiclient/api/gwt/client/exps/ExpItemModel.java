/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.NodeFields;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
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

	public Long getMessageCount() {
		return (Long) this.getProperty("messageCount");
	}

	public Long getConnectionCount() {
		return (Long) this.getProperty("connectionCount");
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

	public ImageUrl getIconUrl() {
		String rt = (String) this.getProperty("icon");
		return ImageUrl.parse(rt, true);

	}

	public ImageUrl getImageUrl() {

		String rt = (String) this.getProperty("image");

		return ImageUrl.parse(rt, true);

	}

	public ImageUrl getUserIconAsImageUrl() {
		String str = (String) this.getProperty("userIcon");
		return ImageUrl.parse(str, true);

	}

	public String getAccountId() {
		return (String) this.getProperty("accountId");
	}

	public String getNick() {
		return (String) this.getProperty("nick");
	}

}
