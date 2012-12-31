/**
 * Jul 22, 2012
 */
package com.fs.commons.api.service.support;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.commons.api.message.MessageContext;

/**
 * @author wu
 * 
 */
public abstract class ServiceContextConverterSupport<T> extends
		ConverterSupport<MessageContext, T> {

	/** */
	public ServiceContextConverterSupport(Class<T> tc, ConverterI.FactoryI fa) {
		super(MessageContext.class, tc, fa);

	}

}
