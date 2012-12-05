/**
 * Jun 19, 2012
 */
package com.fs.commons.impl.test.config;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;

/**
 * @author wuzhen
 * 
 */
public class TestConfigurable extends ConfigurableSupport implements
		TestInterface {

	public TestConfigurable2 testConfigurable2;

	/**
	 * @param cfg
	 */
	public TestConfigurable() {
		super();

	}

	/*
	
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

	}

}
