/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.webserver.api.support;

import javax.servlet.http.HttpServlet;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class ConfigurableServletSupport extends HttpServlet implements ConfigurableI, ActivableI {

	protected Configuration config;
	
	protected ContainerI container;
	
	@Override
	public void active(ActiveContext ac) {
		//
		this.container = ac.getContainer();
	}

	@Override
	public void deactive(ActiveContext ac) {
		//
		//
	}

	@Override
	public void configure(Configuration cfg) {
		//
		this.config = cfg;
		//
	}

	@Override
	public void configure(String id, ConfigurationProviderI cp) {
		//
		throw new FsException("TODO");
		//
	}

	@Override
	public Configuration getConfiguration() {
		//
		return this.config;
		//
	}

}
