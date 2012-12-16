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

	/**
	 * @param target
	 */
	public WebSocketEW(EventGd target) {
		super(target);
	}

	public String getWebSocketId() {
		return (String) this.target.getPayload("_webSocketId");
	}

}
