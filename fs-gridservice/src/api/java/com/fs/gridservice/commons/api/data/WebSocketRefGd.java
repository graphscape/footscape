/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.data;

import com.fs.gridservice.commons.api.gobject.EndPointGoI;

/**
 * @author wu
 * 
 */
public class WebSocketRefGd extends ObjectRefGd<EndPointGoI> {

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
