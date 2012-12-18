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
public abstract class WsMsgEW extends WebSocketEW {

	public static final EventType TYPE = EventType.valueOf("WebSocketMessage");

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

	protected WsMsgEW(EventGd target, String wsId) {
		super(target, wsId);
	}

	/**
	 * @param target
	 */
	public WsMsgEW(EventGd target) {
		super(target);
	}

}