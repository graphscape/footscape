/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 15, 2012
 */
package com.fs.expector.api.gobject;

import com.fs.commons.api.message.MessageI;
import com.fs.expector.api.GridedObjectI;

/**
 * @author wu
 * 
 */
public interface WebSocketGoI extends GridedObjectI {

	public void sendReady();
	
	public void sendMessage(MessageI msg);

	public void sendTextMessage(String msg);

}
