/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventResponse;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class TerminalMsgReceiveEW extends TerminalMsgEW {

	public static final EventType TYPE = EventType.valueOf("TerminalMessageReceiving");

	public static class Response extends EventResponse<TerminalMsgReceiveEW, TerminalMsgSendEW> {

		public Response(TerminalMsgReceiveEW s, TerminalMsgSendEW r) {
			super(s, r);
		}

	}

	public static TerminalMsgReceiveEW valueOf(Path path, String tId, String cid, MessageI msg) {
		String mid = msg.getId();
		TerminalMsgReceiveEW rt = new TerminalMsgReceiveEW(new EventGd(TYPE, path, mid, msg.getPath()), tId,
				cid);
		rt.setMessage(msg);
		return rt;
	}

	// For wrapper
	public TerminalMsgReceiveEW(EventGd target) {
		super(target);
	}

	/**
	 * @param target
	 */
	protected TerminalMsgReceiveEW(EventGd target, String tId, String cid) {
		super(target, tId, cid);
	}

}
