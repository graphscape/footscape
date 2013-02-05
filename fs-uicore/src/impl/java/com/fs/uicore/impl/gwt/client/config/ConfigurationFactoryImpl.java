/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 21, 2012
 */
package com.fs.uicore.impl.gwt.client.config;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI;
import com.fs.uicore.api.gwt.client.config.UiConfiguration;
import com.fs.uicore.api.gwt.client.consts.UiConstants;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.support.ContainerAwareUiObjectSupport;

/**
 * @author wu
 * 
 */
public class ConfigurationFactoryImpl extends ContainerAwareUiObjectSupport implements ConfigurationFactoryI {

	/**
	 * @param name
	 */
	public ConfigurationFactoryImpl(ContainerI c) {
		this(c, null);
	}

	public ConfigurationFactoryImpl(ContainerI c, String name) {
		super(c, name);
	}

	private Map<String, UiConfiguration> configMap = new HashMap<String, UiConfiguration>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	@Override
	public UiConfiguration getDefaultConfiguration() {
		return this.getConfiguration(UiConstants.DEFAULT_CONFIG_ID, true);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI#getConfiguration
	 * (java.lang.String, boolean)
	 */
	@Override
	public UiConfiguration getConfiguration(String id, boolean force) {
		// TODO Auto-generated method stub
		UiConfiguration rt = this.configMap.get(id);

		if (rt == null && force) {
			throw new UiException("force:" + id + " not found");
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI#getConfiguration
	 * (java.lang.String, com.fs.uicore.api.gwt.client.core.UiCallbackI)
	 */
	@Override
	public void getConfiguration(String id, UiCallbackI<UiConfiguration, Object> cb) {
		throw new UiException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.config.ConfigurationFactoryI#
	 * getConfigurationList(java.lang.String[],
	 * com.fs.uicore.api.gwt.client.core.UiCallbackI)
	 */
	@Override
	public void getConfigurationList(String[] ids, UiCallbackI<ConfigurationFactoryI, Object> cb) {
		throw new UiException("TODO");

	}

}
