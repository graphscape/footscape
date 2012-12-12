/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webserver.api.websocket;

import com.fs.commons.api.ActiveContext;

/**
 * @author wu
 *
 */
public interface WsFactoryI {
	
	public WsManagerI addManager(ActiveContext ac, String name);
	
	public WsManagerI getManager(String name, boolean force);
	
}
