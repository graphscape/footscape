/**
 * Jul 12, 2012
 */
package com.fs.webserver.api;

import com.fs.commons.api.ActiveContext;

/**
 * @author wu
 * 
 */
public interface WebAppI {

	public ServletHolderI addServlet(ActiveContext ac, String name, String cfgId);

	public ServletHolderI getServlet(String name);
	
	public WebResourceI addResource(ActiveContext ac, String name, String cfgId);
	
	public WebResourceI getResource(String name);

}
