/**
 * Jul 12, 2012
 */
package com.fs.webserver.api;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;

/**
 * @author wu
 * 
 */
public interface WebAppI {

	public ServletHolderI addServlet(ActiveContext ac, String name, String cfgId);

	public ServletHolderI addServlet(ActiveContext ac, String name, Configuration cfg);

	public ServletHolderI getServlet(String name);

	public WebResourceI addResource(ActiveContext ac, String name, String cfgId);

	public WebResourceI getResource(String name);

	public void setWellcomeFiles(String[] wfs);

}
