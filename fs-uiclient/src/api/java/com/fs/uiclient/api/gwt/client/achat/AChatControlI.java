/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.api.gwt.client.achat;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface AChatControlI extends ControlI {
	
	public void join();
	
	public void join(String actId);
	
	public void send(String actId, String message);//
}
