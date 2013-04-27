/**
 *  
 */
package com.fs.uiclient.api.gwt.client.user;

import java.util.Date;

import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.DateData;

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

	public DateData getBirthDay() {
		return (DateData) this.getProperty("birthDay", true);
	}

	public int getAge() {
		return new Date().getYear() - new Date(this.getBirthDay().getValue()).getYear();
	}

	/**
	 * @return
	 */
	public String getIconUrlData() {
		String rt = (String) this.getProperty("icon", true);
		return rt.equals("n/a") ? null : rt;

	}

}
