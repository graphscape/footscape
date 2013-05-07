/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper;

import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.EventWrapper;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public abstract class TerminalEW extends EventWrapper {

	public static final EventType TYPE = EventType.valueOf("TermianlEvent");

	public static final String HK_TID = "_terminalId";

	public static final String HK_CID = "_clientId";

	/**
	 * @param target
	 */
	public TerminalEW(EventGd target, String tid, String cid) {
		super(target);
		this.target.setHeader(HK_TID, tid);
		this.target.setHeader(HK_CID, cid);
	}

	public TerminalEW(EventGd target) {
		super(target);

	}

	public String getTerminalId() {
		return this.getTerminalId(false);
	}
	
	public String getTerminalId(boolean force) {
		return (String) this.target.getHeader(HK_TID,force);
	}

	public String getClientId() {
		return (String) this.target.getHeader(HK_CID);
	}

}
