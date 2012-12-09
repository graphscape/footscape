/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.ldata.LocalDataWrapper;

/**
 * @author wu
 * 
 */
public class AccountsLDW extends LocalDataWrapper {

	public static AccountsLDW ME = new AccountsLDW();

	public static AccountsLDW getInstance() {
		return ME;
	}

	/**
	 * @param ld
	 */
	protected AccountsLDW() {
		super("accounts");
	}

	public AnonymousAccountLDW getAnonymous() {
		return new AnonymousAccountLDW(this.getChild("anonymous"));

	}

	public RegisteredAccountLDW getRegistered() {
		return new RegisteredAccountLDW(this.getChild("registered"));
	}
}
