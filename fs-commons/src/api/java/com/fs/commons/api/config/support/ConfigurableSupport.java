/**
 * Jun 15, 2012
 */
package com.fs.commons.api.config.support;

import com.fs.commons.api.config.ConfigurableI;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.support.ActivableSupport;

/**
 * @author wuzhen
 * 
 */
public class ConfigurableSupport extends ActivableSupport implements
		ConfigurableI {

	protected ConfigurationProviderI configurationProvider;

	protected String configId;

	protected Configuration config;

	public ConfigurableSupport() {

	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	/*
	
	 */
	@Override
	public void configure(String id, ConfigurationProviderI cp) {
		this.configurationProvider = cp;
		this.configId = id;
		Configuration cfg = this.configurationProvider.getConfiguration(id);
		this.configure(cfg);
	}

	@Override
	public void configure(Configuration cfg) {
		this.config = cfg;
		if (this.config == null) {
			this.config = Configuration.nullConfig();
		}
		this.configId = this.config.getId();//
	}

}
