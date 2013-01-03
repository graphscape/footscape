/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicore.api.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class UserInfo extends ObjectPropertiesData {
	public String getAccountId() {
		return this.getString("accountId", true);
	}

	public String getSessionId() {
		return this.getString("sessionId", true);
	}

	public boolean isAnonymous() {
		return this.getBoolean("isAnonymous", false);
	}

}
