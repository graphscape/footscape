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
public class WsMsgSendEW extends WsMsgEW {

	public static final EventType TYPE = EventType
			.valueOf("WebSocketMessageSending");

	public static WsMsgSendEW valueOf(String path, String wsId, MessageI msg) {

		WsMsgSendEW rt = new WsMsgSendEW(new EventGd(TYPE, path), wsId);
		rt.setMessage(msg);
		return rt;
	}

	/**
	 * @param target
	 */
	protected WsMsgSendEW(EventGd target, String wsId) {
		super(target, wsId);
	}
	
	public WsMsgSendEW(EventGd target){
		super(target);
	}

}
