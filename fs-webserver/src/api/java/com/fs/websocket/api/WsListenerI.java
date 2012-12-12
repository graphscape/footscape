/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.websocket.api;

/**
 * @author wu
 * 
 */
public interface WsListenerI {



	public void onConnect(WebSocketI ws);

	public void onMessage(WebSocketI ws, String ms);
	
	public void onException(WebSocketI ws, Throwable t);

	public void onClose(WebSocketI ws, int statusCode, String reason);

}
