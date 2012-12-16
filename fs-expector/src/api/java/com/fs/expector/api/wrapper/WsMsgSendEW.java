/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.expector.api.EventType;
import com.fs.expector.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class WsMsgSendEW extends WsMsgEW {

	public static final EventType TYPE = EventType.valueOf("WebSocketMessageSending");

	public static WsMsgSendEW valueOf(String path, MessageI msg) {

		WsMsgSendEW rt = new WsMsgSendEW(new EventGd(TYPE, path));
		rt.setMessage(msg);
		return rt;
	}

	/**
	 * @param target
	 */
	public WsMsgSendEW(EventGd target) {
		super(target);
	}

}
