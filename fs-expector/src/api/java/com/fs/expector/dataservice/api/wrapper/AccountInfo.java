/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class AccountInfo extends NodeWrapper {

	public static final NodeType TYPE = NodeType.valueOf("accountInfo");// account

	public static final String PK_ACCOUNT_ID = "accountId";

	public static final String PK_PASSWORD = "password";

	public static final String EMAIL = "email";

	/**
	 * @param pts
	 */
	public AccountInfo() {
		super(TYPE);
	}

	public static void config(NodeConfigurations cfs) {
		cfs.addConfig(TYPE, AccountInfo.class).field(PK_ACCOUNT_ID)
				.field(PK_PASSWORD).field(EMAIL);

	}
	
	public void setAccountId(String accid){
		this.setProperty(PK_ACCOUNT_ID, accid);
	}
	public String getAccountId(){
		return this.getPropertyAsString(PK_ACCOUNT_ID);	
	}
	public void setPassword(String password) {
		this.setProperty(PK_PASSWORD, password);
	}

	public String getPassword() {
		return (String) this.target.getProperty(PK_PASSWORD);
	}

	public String getEmail() {
		return (String) this.target.getProperty(EMAIL);
	}

	public void setEmail(String email) {
		this.setProperty(EMAIL, email);
	}

}
