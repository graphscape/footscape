/**
 *  
 */
package com.fs.uiclient.api.gwt.client.user;

import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * @author wu
 * 
 */
public class UserInfo extends PropertiesData<Object> {

	public UserInfo(PropertiesData<Object> pts) {
		super();
		this.setProperties(pts);
	}

	public String getAccountId() {
		return (String) this.getProperty("accountId", true);
	}

	public String getNick() {
		return (String) this.getProperty("nick", true);
	}

	public String getGender() {
		return (String) this.getProperty("gender", true);
	}

	public int getAge() {
		return (Integer) this.getProperty("age", true);
	}

	/**
	 * @return
	 */
	public String getIconUrlData() {
		String rt = (String) this.getProperty("icon", true);
		return rt.equals("n/a") ? null : rt;

	}

}
