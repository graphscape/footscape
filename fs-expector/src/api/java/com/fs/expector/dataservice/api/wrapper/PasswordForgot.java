/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 */
public class PasswordForgot extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("password-forgot");// account

	public static final String PK_ACCOUNT_ID = "accountId";

	/**
	 * @param pts
	 */
	public PasswordForgot() {
		super(TYPE);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(TYPE, PasswordForgot.class).field(PK_ACCOUNT_ID);

	}

	public void setAccountId(String accid) {
		this.setProperty(PK_ACCOUNT_ID, accid);
	}

	public String getAccountId() {
		return this.getPropertyAsString(PK_ACCOUNT_ID);
	}

}
