/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.expapp.wrapper;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.dataservice.api.expapp.NodeTypes;

/**
 * @author wu
 * 
 */
public class Profile extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("profile");

	public static final String ACCOUNTID = "accountId";

	public static final String ICON = "icon";

	public static final String AGE = "age";

	public static final String GENDER = "gender";

	/**
	 * @param pts
	 */
	public Profile() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(TYPE, Profile.class).field(ACCOUNTID).field(AGE)
				.field(GENDER).field(ICON);

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

	public Integer getAge() {
		return (Integer) this.target.getProperty(AGE);
	}

	public void setAge(Integer a) {
		this.target.setProperty(AGE, a);
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
