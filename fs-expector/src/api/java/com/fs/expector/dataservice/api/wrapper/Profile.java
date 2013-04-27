/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import java.util.Date;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.meta.FieldType;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public class Profile extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("profile");

	public static final String ACCOUNTID = "accountId";

	public static final String ICON = "icon";

	public static final String BIRTHDAY = "birthDay";

	public static final String GENDER = "gender";

	/**
	 * @param pts
	 */
	public Profile() {
		super(TYPE);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(TYPE, Profile.class).field(ACCOUNTID).field(BIRTHDAY, FieldType.DATE).field(GENDER)
				.field(ICON);

	}

	public void setAccountId(String email) {
		this.setProperty(ACCOUNTID, email);
	}

	public String getAccountId() {
		return (String) this.target.getProperty(ACCOUNTID);
	}

	public String getGender() {
		return (String) this.target.getProperty(GENDER);
	}

	public void setGender(String g) {
		this.target.setProperty(GENDER, g);
	}

	public Date getBirthDay() {
		return (Date) this.target.getProperty(BIRTHDAY);
	}

	public void setBirthDay(Date a) {
		this.target.setProperty(BIRTHDAY, a);
	}

	/**
	 * Oct 29, 2012
	 */
	public String getIcon() {
		//
		return (String) this.target.getProperty(ICON);

	}

	/**
	 * Nov 2, 2012
	 */
	public void setIcon(String icon) {
		this.setProperty(ICON, icon);
	}

}
