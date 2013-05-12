/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet;

import com.fs.uicore.api.gwt.client.HandlerI;

/**
 * @author wu
 * 
 */
public interface GometI {
	
	public String getProtocol();
	
	public void open(long timeoutMs);

	public void close();

	public void send(String jsS);

	//regist listeners
	public void addOpenHandler(HandlerI<GometI> handler);

	public void addCloseHandler(HandlerI<String> handler);

	public void addErrorHandler(HandlerI<String> handler);

	public void addMessageHandler(HandlerI<String> handler);

	public boolean isOpen();

}
