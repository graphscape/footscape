/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.fs.webcomet.api;

import java.util.List;


/**
 * @author wu
 * 
 */
public interface CometManagerI {

	public CometProtocolI getProtocol();
	
	public String getName();

	public void addListener(CometListenerI ln);

	public List<CometI> getSocketList();
	
	public CometI getSocket(String id);

	public CometI getSocket(String id, boolean b);
	
	public void addInterceptor(CometCreatingInterceptorI ci);

}
