/**
 * Jul 9, 2012
 */
package com.fs.commons.impl.config;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.wrapper.PropertiesWrapper;
import com.fs.commons.impl.config.properties.PropertiesConfigurationProvider;
import com.fs.commons.impl.config.support.ConfigurationProviderSupport;
import com.fs.commons.impl.config.xml.XmlConfigurationProvider;

/**
 * @author wu
 * 
 */
public class MultiConfigurationProvider extends ConfigurationProviderSupport {

	private List<ConfigurationProviderSupport> childList = new ArrayList<ConfigurationProviderSupport>();

	public MultiConfigurationProvider() {
		this.childList.add(new PropertiesConfigurationProvider());
		this.childList.add(new XmlConfigurationProvider());

	}

	@Override
	public PropertiesWrapper loadAlias(String id) {
		PropertiesWrapper rt = null;
		for (ConfigurationProviderSupport cp : this.childList) {
			PropertiesWrapper cfg = cp.loadAlias(id);
			if (rt == null) {
				rt = cfg;
			} else {
				rt = rt.mergeFrom(cfg);
			}
		}
		return rt;
	}

	@Override
	public PropertiesWrapper loadConfig(String id) {
		//
		PropertiesWrapper rt = null;
		for (ConfigurationProviderSupport cp : this.childList) {
			PropertiesWrapper cfg = cp.loadConfig(id);
			if (rt == null) {
				rt = cfg;
			} else {
				rt = rt.mergeFrom(cfg);
			}
		}
		return rt;
		//
	}

}
