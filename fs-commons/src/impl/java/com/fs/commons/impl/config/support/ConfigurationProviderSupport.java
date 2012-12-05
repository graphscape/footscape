/**
 * Jun 15, 2012
 */
package com.fs.commons.impl.config.support;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.wrapper.PropertiesWrapper;

/**
 * @author wuzhen
 * 
 */
public abstract class ConfigurationProviderSupport implements
		ConfigurationProviderI {

	/*
	
	 */
	@Override
	public void add(Configuration cfg) {
		throw new FsException("read only.");
	}

	/*
	
	 */
	@Override
	public Configuration getConfiguration(String id) {

		//
		PropertiesWrapper pw = this.loadConfig(id);// load properties
		//
		PropertiesWrapper alias = this.loadAlias(id);
		//
		for (String key : pw.keyList()) {
			String v = pw.getProperty(key);
			if (v == null || !v.startsWith("$")) {
				continue;
			}
			String a = v.substring(1);
			String av = alias.getProperty(a);
			if (av == null) {
				throw new FsException("alias not resolved," + key + "=" + v
						+ "");
			}
			pw.setProperty(key, av);
		}
		return new Configuration(id, this, pw);
	}

	public abstract PropertiesWrapper loadAlias(String id);

	public abstract PropertiesWrapper loadConfig(String id);

}
