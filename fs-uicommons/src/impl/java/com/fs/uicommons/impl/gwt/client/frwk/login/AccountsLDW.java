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
public class AccountsLDW extends LocalDataWrapper {

	public static AccountsLDW ME = new AccountsLDW();

	public static AccountsLDW getInstance() {
		return ME;
	}

	protected AccountsLDW() {
		super("accounts");
	}

	/**
	 * @param ld
	 */
	protected AccountsLDW(LocalData ld) {
		super(ld);
	}

	public AnonymousAccountLDW getAnonymous() {
		return new AnonymousAccountLDW(this.getChild("anonymous"));

	}

	public RegisteredAccountLDW getRegistered() {
		return new RegisteredAccountLDW(this.getChild("registered"));
	}

	/**
	 * Jan 2, 2013
	 */
	public void invalid() {
		this.getAnonymous().invalid();
		this.getRegistered().invalid();
	}
}
