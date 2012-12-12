/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.api.websocket;

/**
 * @author wu
 * 
 */
public interface WsListenerI {
	
	public void onSocketOpen(WebSocketI ws);
	
	public void onMessage(WebSocketI ws, String message);
	
}
