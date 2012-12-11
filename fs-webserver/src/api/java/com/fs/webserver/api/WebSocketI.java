/**
 *  Dec 11, 2012
 */
package com.fs.webserver.api;

/**
 * @author wuzhen
 * 
 */
public interface WebSocketI {

	public static interface FactoryI {
		public void addWebSocket(WebSocketI ws);
	}

	public void onMessage(String message);

}
