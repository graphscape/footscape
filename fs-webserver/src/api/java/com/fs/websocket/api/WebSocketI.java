/**
 *  Dec 11, 2012
 */
package com.fs.websocket.api;

/**
 * @author wuzhen
 * 
 */
public interface WebSocketI {

	public String getId();

	public void sendMessage(String msg);

	public void addListener(WsListenerI ln);

}
