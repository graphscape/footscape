/**
 * Jun 20, 2012
 */
package com.fs.commons.impl.factory;

import java.util.List;

import com.fs.commons.api.factory.ObjectConfigI;
import com.fs.commons.api.support.ObjectFinderSupport;

/**
 * @author wuzhen
 * 
 */
public class ConfigFinderImpl extends ObjectFinderSupport<ObjectConfigI> {

	private ConfigFactoryImpl cfi;

	/**
	 * @param c
	 * @param cls
	 */
	public ConfigFinderImpl(ConfigFactoryImpl cfi) {
		this.cfi = cfi;
	}

	/*
	
	 */
	@Override
	public List<ObjectConfigI> find() {
		return cfi.find(this);
	}

}
