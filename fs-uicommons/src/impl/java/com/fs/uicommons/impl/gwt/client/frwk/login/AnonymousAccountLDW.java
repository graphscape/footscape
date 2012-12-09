/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.ldata.LocalData;

/**
 * @author wu
 * 
 */
public class AnonymousAccountLDW extends AbstractAccountLDW {

	private static String K_ACCOUNT = "account";

	public AnonymousAccountLDW(LocalData ld) {
		super(ld);
	}

	public void save(String accId, String password) {
		this.setValue(K_ACCOUNT, accId);
		this.setPassword(password);
		this.valid(true);
	}

	public String getAccountId() {
		return this.getValue(K_ACCOUNT);
	}

}
