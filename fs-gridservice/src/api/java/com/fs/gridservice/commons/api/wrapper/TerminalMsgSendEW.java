/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class TerminalMsgSendEW extends TerminalMsgEW {

	public static final EventType TYPE = EventType.valueOf("WebSocketMessageSending");

	public static TerminalMsgSendEW valueOf(Path path, String tid, String cid, MessageI msg) {

		TerminalMsgSendEW rt = new TerminalMsgSendEW(new EventGd(TYPE, path, msg.getPath()), tid, cid);
		rt.setMessage(msg);
		return rt;
	}

	/**
	 * @param target
	 */
	protected TerminalMsgSendEW(EventGd target, String tid, String cid) {
		super(target, tid, cid);
	}

	public TerminalMsgSendEW(EventGd target) {
		super(target);
	}

}
