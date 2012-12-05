/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 16, 2012
 */
package com.fs.uixmpp.core.impl.servlet;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.lang.Todo;

/**
 * @author wu
 * 
 */
public class BoshProxyServlet extends ProxyServlet implements ConfigurableI,
		ActivableI {

	protected Configuration config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#active(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void active(ActiveContext ac) {
		this.remotePath = this.config.getProperty("path");
		this.remotePort = this.config.getPropertyAsInt("port", 5280);
		this.remoteServer = this.config.getProperty("host");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.ActivableI#deactive(com.fs.commons.api.ActiveContext)
	 */
	@Override
	public void deactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.config.ConfigurableI#configure(com.fs.commons.api.
	 * config.Configuration)
	 */
	@Override
	public void configure(Configuration cfg) {
		this.config = cfg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.config.ConfigurableI#configure(java.lang.String,
	 * com.fs.commons.api.config.ConfigurationProviderI)
	 */
	@Override
	public void configure(String id, ConfigurationProviderI cp) {
		throw new Todo("");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.config.ConfigurableI#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() {
		throw new Todo("");

	}

}
