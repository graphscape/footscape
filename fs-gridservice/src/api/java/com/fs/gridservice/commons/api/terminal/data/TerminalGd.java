/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.terminal.data;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public class TerminalGd extends EntityGd {

	public static final String PK_ADDRESS = "_address";

	public static final String PK_CLIENTID = "_clientId";

	public static final String PK_SESSIONID = "_sessionId";

	public TerminalGd() {

	}

	public TerminalGd(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getSessionId(boolean force) {
		return this.getString(PK_SESSIONID, force);
	}

	public String getClientId(boolean force) {
		return (String) this.getProperty(PK_CLIENTID, force);

	}

	public String getAddress() {
		return (String) this.getProperty(PK_ADDRESS);

	}

}