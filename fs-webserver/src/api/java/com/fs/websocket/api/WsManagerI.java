/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.websocket.api;

import java.util.List;

/**
 * @author wu
 * 
 */
public interface WsManagerI {

	public String getName();

	public void addListener(WsListenerI ln);

	public List<WebSocketI> getSocketList();
	
	public WebSocketI getSocket(String id);

	public WebSocketI getSocket(String id, boolean b);
	
	public void addInterceptor(WsCreatingInterceptorI ci);

}
