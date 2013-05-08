/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webcomet.api;

import java.io.Reader;


/**
 * @author wu
 * 
 */
public interface CometListenerI {


	public void onConnect(CometI ws);

	public void onMessage(CometI ws, Reader reader);
	
	public void onMessage(CometI ws, String ms);
	
	public void onException(CometI ws, Throwable t);

	public void onClose(CometI ws, int statusCode, String reason);

}
