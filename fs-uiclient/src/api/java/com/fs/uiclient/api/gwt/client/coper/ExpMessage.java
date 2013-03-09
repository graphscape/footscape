/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 4, 2012
 */
package com.fs.uiclient.api.gwt.client.coper;

import com.fs.uicore.api.gwt.client.MsgWrapper;
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
		return (String) this.getHeader("id", true);
	}

	/**
	 * @return the expId1
	 */
	public String getExpId1() {
		return (String) this.getHeader("expId1", true);
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

	/**
	 * @return the accountId2
	 */
	public String getAccountId2() {

		return (String) this.getHeader("accountId2", true);

	}

}
