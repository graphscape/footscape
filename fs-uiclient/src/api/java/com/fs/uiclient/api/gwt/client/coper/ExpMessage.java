/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.uiclient.api.gwt.client.coper;

import com.fs.uiclient.api.gwt.client.NodeFields;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class ExpMessage extends MsgWrapper {

	/**
	 * @param name
	 */
	public ExpMessage(MessageData msg) {
		super(msg);
	}

	public String getId() {
		return (String) this.getHeader(NodeFields.PK_ID, true);
	}

	/**
	 * @return the expId1
	 */
	public String getExpId1() {
		return (String) this.getHeader("expId1", true);
	}

	/**
	 * @return the expId1
	 */
	public String getExpTitle1() {
		return (String) this.getPayload("expTitle1", true);
	}
	/**
	 * @return the expId1
	 */
	public String getExpBody1() {
		return (String) this.getPayload("expBody1", true);
	}

	/**
	 * @return the expId2
	 */
	public String getExpId2() {
		return (String) this.getHeader("expId2", true);
	}

	/**
	 * @return the accountId1
	 */
	public String getAccountId1() {
		return (String) this.getHeader("accountId1", true);
	}

	public String getNick1() {
		return (String) this.getPayload("nick1", true);
	}

	public String getIcon1() {
		String rt = (String) this.getPayload("icon1", false);
		if (rt == null) {
			rt = UiClientConstants.DEF_ICON;
		}
		return rt;

	}

	public DateData getTimeStamp() {
		return (DateData) this.getPayload(NodeFields.PK_TIMESTAMP, true);
	}

	/**
	 * @return the accountId2
	 */
	public String getAccountId2() {

		return (String) this.getHeader("accountId2", true);

	}

}
