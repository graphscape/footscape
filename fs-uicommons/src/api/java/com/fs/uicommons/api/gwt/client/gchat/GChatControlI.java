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

	public static final String D_NAME = "gchat";//

	public void setConnected(boolean c);

	public void join();

	public void join(String gid);

	public void send();

	public void send(String gid, String text);

	public GChatModel getOrCreateChatModel();

	public boolean isConnected();

}
