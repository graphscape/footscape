/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.message.converter;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.support.MessageContextConverterSupport;

/**
 * @author wu
 * 
 */
public class MessageContextC extends
		MessageContextConverterSupport<MessageContext> {

	/** */
	public MessageContextC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(MessageContext.class, fa);

	}

	/* */
	@Override
	public MessageContext convert(MessageContext f) {

		return f;

	}
}
