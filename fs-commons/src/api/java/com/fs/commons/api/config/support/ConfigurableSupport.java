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
 *         <p>
 *         TODO move to common.support package
 */
public class ConfigurableSupport extends ActivableSupport implements
		ConfigurableI {

	protected ConfigurationProviderI configurationProvider;

	protected String configId;

	protected Configuration config;

	protected String name;

	public ConfigurableSupport() {

	}

	/**
	 * @param name2
	 */
	public ConfigurableSupport(String name2) {
		this.name = name2;
	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	protected String getName() {
		if (this.name == null) {
			return this.config.getName();
		} else {
			return this.name;
		}
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
