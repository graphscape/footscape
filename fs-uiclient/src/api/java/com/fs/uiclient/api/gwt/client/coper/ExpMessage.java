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

	/**
	 * @return the expId1
	 */
	public String getExpId1() {
		return (String) this.getPayload("expId1", true);
	}

	/**
	 * @return the expId2
	 */
	public String getExpId2() {
		return (String) this.getPayload("expId2", true);
	}

	/**
	 * @return the accountId1
	 */
	public String getAccountId1() {
		return (String) this.getPayload("accountId1", true);
	}

	/**
	 * @return the accountId2
	 */
	public String getAccountId2() {

		return (String) this.getPayload("accountId2", true);

	}

	/**
	 * Mar 7, 2013
	 */
	public String getBody() {
		//
		return (String) this.getPayload("body", true);
	}

}
