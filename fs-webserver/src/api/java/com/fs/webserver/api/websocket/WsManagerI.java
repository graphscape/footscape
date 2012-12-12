/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.api.websocket;

/**
 * @author wu
 * 
 */
public interface WsManagerI {
	
	public String getName();
	
	public void addListener(WsListenerI ln);

	public WebSocketI getSocket(String id);
}
