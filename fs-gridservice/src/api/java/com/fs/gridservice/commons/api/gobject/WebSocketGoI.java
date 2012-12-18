/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.gridservice.commons.api.gobject;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.GridedObjectI;

/**
 * @author wu
 * 
 */
public interface WebSocketGoI extends GridedObjectI {

	public static final String P_READY = "/control/status/serverIsReady";

	public void sendReady();

	public void sendMessage(MessageI msg);

	public void sendTextMessage(String msg);

}
