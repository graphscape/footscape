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

	/**
	 * @param target
	 */
	public TerminalEW(EventGd target, String tid) {
		super(target);
		this.target.setHeader(HK_TID, tid);
	}

	public TerminalEW(EventGd target) {
		super(target);

	}

	public String getTerminalId() {
		return (String) this.target.getHeader(HK_TID);
	}

}
