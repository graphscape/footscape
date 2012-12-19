/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.presence.data;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public class PresenceGd extends EntityGd {

	public static final String PK_STATUS = "_status";

	public static final String PK_ACCOUNTID = "_accountId";

	public static final String PK_TERMINALID = "_terminalId";

	public PresenceGd() {

	}

	public PresenceGd(String id) {
		super(id);
	}

	public PresenceGd(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getStatus() {
		return (String) this.getProperty(PK_STATUS);
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		this.setProperty(PK_STATUS, string);
	}

	public String getTerminalId() {
		return this.getString(PK_TERMINALID);
	}

	public void setTerminalId(String string) {
		this.setProperty(PK_TERMINALID, string);
	}

}
