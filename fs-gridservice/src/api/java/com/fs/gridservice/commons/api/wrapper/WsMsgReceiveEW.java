/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.wrapper;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.EventResponse;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class WsMsgReceiveEW extends WsMsgEW {

	public static final EventType TYPE = EventType.valueOf("WebSocketMessageReceiving");

	public static class Response extends EventResponse<WsMsgReceiveEW, WsMsgSendEW> {

		public Response(WsMsgReceiveEW s, WsMsgSendEW r) {
			super(s, r);
		}

	}

	public static WsMsgReceiveEW valueOf(String path,String wsoId, MessageI msg) {

		WsMsgReceiveEW rt = new WsMsgReceiveEW(new EventGd(TYPE, path),wsoId);
		rt.setMessage(msg);
		return rt;
	}

	//For wrapper
	public WsMsgReceiveEW(EventGd target){
		super(target);
	}
	
	/**
	 * @param target
	 */
	protected WsMsgReceiveEW(EventGd target,String wsId) {
		super(target,wsId);
	}

}
