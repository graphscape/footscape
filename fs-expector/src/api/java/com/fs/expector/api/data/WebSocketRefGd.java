/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api.data;

import com.fs.expector.api.gobject.WebSocketGoI;

/**
 * @author wu
 * 
 */
public class WebSocketRefGd extends ObjectRefGd<WebSocketGoI> {

	/**
	 * @param id
	 * @param mid
	 */
	public WebSocketRefGd(String id, String mid) {
		super(id, mid);
	}

	public static String PK_WEBSERVERID = "webserverId";

	public static String PK_SESSIONID = "sessionId";

	public static String PK_WEBSOCKETID = "websocketId";

	// public void addListener();

}
