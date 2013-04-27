/**
 * Jul 10, 2012
 */
package com.fs.webserver.api;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.server.ServerI;

/**
 * @author wu
 * 
 */
public interface WebServerI extends ServerI {

	public WebAppI addWebApp(ActiveContext ac, String name, String cfgId);

	public WebAppI getWebApp(String name);
	
	public WebAppI getRootWebApp();

}
