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
public class ClientGd extends EntityGd {

	public static final String TERMIANAlID = "terminalId";

	public static final String SESSIONID = "sessionId";

	public ClientGd() {

	}

	/**
	 * @param pts
	 */
	public ClientGd(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getSessionId(boolean force) {
		return (String) this.getString(SESSIONID, force);
	}

	public String getTerminalId() {
		return (String) this.getString(TERMIANAlID);
	}

}
