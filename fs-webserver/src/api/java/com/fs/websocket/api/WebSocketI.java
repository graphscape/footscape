/**
 *  Dec 11, 2012
 */
package com.fs.websocket.api;

import com.fs.commons.api.context.ContextI;

/**
 * @author wuzhen
 * 
 */
public interface WebSocketI extends ContextI {

	public String getId();

	public void sendMessage(String msg);

	public void addListener(WsListenerI ln);

}
