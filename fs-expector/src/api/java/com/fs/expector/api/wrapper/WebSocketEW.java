/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.expector.api.wrapper;

import com.fs.expector.api.EventType;
import com.fs.expector.api.EventWrapper;
import com.fs.expector.api.data.EventGd;

/**
 * @author wu
 * 
 */
public abstract class WebSocketEW extends EventWrapper {

	public static final EventType TYPE = EventType.valueOf("WebSocket");

	public static final String HK_WSOID = "_webSocketObjectId";

	/**
	 * @param target
	 */
	public WebSocketEW(EventGd target, String wsoId) {
		super(target);
		this.target.setHeader(HK_WSOID, wsoId);
	}

	public WebSocketEW(EventGd target) {
		super(target);

	}

	public String getWebSocketId() {
		return (String) this.target.getHeader(HK_WSOID);
	}

}
