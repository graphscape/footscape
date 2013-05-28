/**
 */
package com.fs.commons.impl.config.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.ConfigurationProviderI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public abstract class ConfigurationProviderSupport implements ConfigurationProviderI {

	private Map<String, Configuration> cfgCache;

	public ConfigurationProviderSupport() {
		this.cfgCache = new HashMap<String, Configuration>();
	}

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
		return this.getConfiguration(id, true);
	}

	@Override
	public Configuration getConfiguration(String id, boolean cache) {
		if (!cache) {
			return this.doGetConfiguration(id);
		}
		Configuration rt = this.cfgCache.get(id);

		if (rt != null) {
			return rt;
		}
		rt = this.doGetConfiguration(id);
		this.cfgCache.put(id, rt);
		return rt;

	}

	protected Configuration doGetConfiguration(String id) {
		//
		List<String> childIdSet = new ArrayList<String>();
		Map<String, String> configRefMap = new HashMap<String, String>();
		Set<String> typeHolder = new HashSet<String>();
		PropertiesI<String> pw = this.loadConfig(id, typeHolder, childIdSet, configRefMap);// load
		// properties
		//
		PropertiesI<String> alias = this.loadAlias(id);
		//
		for (String key : pw.keyList()) {
			String v = pw.getProperty(key);
			if (v == null || !v.startsWith("$")) {
				continue;
			}
			String a = v.substring(1);
			String av = alias.getProperty(a);

			if (av == null) {
				a.startsWith("env:");
				a = a.substring("env:".length());
				av = System.getProperty(a);
			}

			if (av == null) {
				throw new FsException("alias not resolved," + key + "=" + v + "");
			}
			pw.setProperty(key, av);
		}

		Configuration.Type type = typeHolder.isEmpty() ? Configuration.ROOT : Configuration.Type
				.valueOf(typeHolder.iterator().next());

		return new Configuration(id, type, pw, childIdSet, configRefMap);
	}

	public abstract PropertiesI<String> loadAlias(String id);

	public abstract PropertiesI<String> loadConfig(String id, Set<String> typeHolder,
			List<String> childIdSet, Map<String, String> crmap);

}
