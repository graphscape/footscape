/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 10, 2013
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.Constants;
import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * @author wu
 * 
 */
public class ExpConnect extends PropertiesData<Object> {

	public ExpConnect(PropertiesData<Object> pts) {
		this.setProperties(pts);
	}

	public String getConnectId() {
		return (String) this.getProperty(Constants.NK_ID, true);
	}

	public String getAccountId1() {
		return (String) this.getProperty("accountId1", true);
	}

	public String getAccountId2() {

		return (String) this.getProperty("accountId2", true);
	}

	public String getNick2(boolean force) {
		return (String) this.getProperty("nick2", force);
	}

	public String getExpBody2(boolean force) {
		return (String) this.getProperty("expBody2", force);
	}

	public String getExpId1() {
		return (String) this.getProperty("expId1", true);
	}

	public String getExpId2() {

		return (String) this.getProperty("expId2", true);
	}

}
