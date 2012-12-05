/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 16, 2012
 */
package com.fs.uixmpp.core.impl.testsupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.fs.uixmpp.core.impl.servlet.ProxyServlet;
/**
 * @author wu
 *
 */
public class BoshProxyServlet extends ProxyServlet{
	/**
	 * Init
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		remotePath = getInitParameter("remotePath");
		remoteServer = getInitParameter("remoteServer");
		String remotePortStr = getInitParameter("remotePort");
		if (remotePath == null || remoteServer == null)
			throw new ServletException(
					"Servlet needs remotePath & remoteServer.");
		if (remotePortStr != null) {
			try {
				remotePort = Integer.parseInt(remotePortStr);
			} catch (Exception e) {
				throw new ServletException("Port must be a number!");
			}
		} else
			remotePort = 80;
		if ("".equals(remotePath))
			remotePath = ""; // XXX ??? "/"
		else if (remotePath.charAt(0) != '/')
			remotePath = "/" + remotePath;
		debugFlag = "true".equals(getInitParameter("debug"));
		//
		log("remote=" + remoteServer + " " + remotePort + " " + remotePath);
	}

}
