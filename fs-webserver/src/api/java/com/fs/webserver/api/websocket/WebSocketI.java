/**
 *  Dec 11, 2012
 */
package com.fs.webserver.api.websocket;

/**
 * @author wuzhen
 * 
 */
public interface WebSocketI {
	public static interface ListenerI {
		
		public void onMessage(String msg);
		
	}
	public String getId();
	
	public void sendMessage(String msg);
	
	public void addListener(ListenerI ln);
	
}
