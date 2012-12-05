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
public class TestConfigurable2 extends ConfigurableSupport implements
		TestInterface {

	public String value1;
	public String value2;

	/**
	 * @param cfg
	 */
	public TestConfigurable2() {

	}

	/*
	
	 */
	@Override
	public void configure(Configuration cfg) {
		// TODO Auto-generated method stub
		super.configure(cfg);
		this.value1 = cfg.getProperty("name1");
		this.value2 = cfg.getProperty("name2");

	}

}
