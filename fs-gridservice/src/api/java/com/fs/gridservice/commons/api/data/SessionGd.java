/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.data;

import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class SessionGd extends EntityGd {

	public static final String ACCID = "accountId";

	public static final String TERMIANAlID = "terminalId";

	public SessionGd() {

	}

	/**
	 * @param pts
	 */
	public SessionGd(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getAccountId() {
		return (String) this.getProperty(ACCID);
	}

	public String getTerminalId() {
		return (String) this.getString(TERMIANAlID);
	}

}
