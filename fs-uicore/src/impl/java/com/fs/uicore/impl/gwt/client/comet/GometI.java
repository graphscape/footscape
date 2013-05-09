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
	
	public void open();

	public void close();

	public void send(String jsS);

	public void onOpen(HandlerI<GometI> handler);

	public void onClose(HandlerI<String> handler);

	public void onError(HandlerI<String> handler);

	public void onMessage(HandlerI<String> handler);

	public boolean isOpen();

}
