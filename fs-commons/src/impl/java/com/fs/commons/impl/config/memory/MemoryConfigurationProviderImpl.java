/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.config.memory;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;

/**
 * @author wuzhen
 * 
 */
public class MemoryConfigurationProviderImpl implements ConfigurationProviderI {

	private Map<String, Configuration> cfgMap = new HashMap<String, Configuration>();

	/*
	
	 */
	@Override
	public Configuration getConfiguration(String id) {

		return this.cfgMap.get(id);
	}

	/*
	
	 */
	@Override
	public void add(Configuration cfg) {
		this.cfgMap.put(cfg.getId(), cfg);
	}

}
