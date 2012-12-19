/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public abstract class TerminalMsgEW extends TerminalEW {

	public static final EventType TYPE = EventType.valueOf("TerminalMessage");

	public static final String MESSAGE = "_message";

	/**
	 * Dec 16, 2012
	 */
	public void setMessage(MessageI msg) {
		this.target.setPayload(MESSAGE, msg);
	}

	public MessageI getMessage() {
		return (MessageI) this.target.getPayload(MESSAGE);
	}

	protected TerminalMsgEW(EventGd target, String tid) {
		super(target, tid);
	}

	/**
	 * @param target
	 */
	public TerminalMsgEW(EventGd target) {
		super(target);
	}

}
