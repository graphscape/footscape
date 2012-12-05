/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 9, 2012
 */
package com.fs.uiserver.api.session;

/**
 * @author wu
 * 
 */
public class SessionWrapper {

	private SessionContext sessionContext;

	private static String SK_ACCOUNT = "account";

	private SessionWrapper(SessionContext sc) {
		this.sessionContext = sc;
	}

	public static SessionWrapper valueOf(SessionContext sc) {
		return new SessionWrapper(sc);
	}

	public String getAccount() {
		return (String) this.sessionContext.getProperty(SK_ACCOUNT);
	}

}
