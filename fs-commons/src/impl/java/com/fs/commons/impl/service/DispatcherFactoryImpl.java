/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.service;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.service.DispatcherI;

/**
 * @author wuzhen
 * 
 */
public class DispatcherFactoryImpl extends ConfigurableSupport implements
		DispatcherI.FactoryI {

	@Override
	public <T> DispatcherI<T> create(String name) {
		return new DispatcherImpl<T>(name);
	}

}
