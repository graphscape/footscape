/**
 * Jul 22, 2012
 */
package com.fs.commons.api.message.support;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.commons.api.message.MessageContext;

/**
 * @author wu
 * 
 */
public abstract class MessageContextConverterSupport<T> extends
		ConverterSupport<MessageContext, T> {

	/** */
	public MessageContextConverterSupport(Class<T> tc, ConverterI.FactoryI fa) {
		super(MessageContext.class, tc, fa);

	}

}
