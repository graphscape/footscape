/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.ldata.LocalData;
import com.fs.uicommons.api.gwt.client.ldata.LocalDataWrapper;

/**
 * @author wu
 * 
 */
public abstract class AbstractAccountLDW extends LocalDataWrapper {

	private static String K_ISVALID = "isvalid";

	private static String K_PASSWORD = "password";

	public AbstractAccountLDW(LocalData ld) {
		super(ld);
	}

	public boolean isValid() {
		return this.getValueAsBoolean(K_ISVALID);
	}

	public void valid(boolean v) {
		this.setValue(K_ISVALID, v);

	}

	public void invalid() {
		this.valid(false);
	}

	public void setPassword(String pw) {
		this.setValue(K_PASSWORD, pw);
	}

	public String getPassword() {
		return this.getValue(K_PASSWORD);
	}

}
