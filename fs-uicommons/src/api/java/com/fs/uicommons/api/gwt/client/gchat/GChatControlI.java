/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface GChatControlI extends ControlI {

	public void join();

	public void send();

	public GChatModel getChatModel();
	
	public boolean isConnected();


}
