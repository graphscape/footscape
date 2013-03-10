/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 10, 2013
 */
package com.fs.uiclient.api.gwt.client.uexp;

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
		return (String) this.getProperty("id");
	}

	public String getAccountId1() {
		return (String) this.getProperty("accountId1");
	}

	public String getAccountId2() {

		return (String) this.getProperty("accountId2");
	}

	public String getExpId1() {
		return (String) this.getProperty("expId1");
	}

	public String getExpId2() {

		return (String) this.getProperty("expId2");
	}

}
