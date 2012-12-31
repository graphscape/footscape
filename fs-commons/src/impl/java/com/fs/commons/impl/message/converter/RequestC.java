/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.message.converter;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.service.support.ServiceContextConverterSupport;

/**
 * @author wu
 * 
 */
public class RequestC extends ServiceContextConverterSupport<MessageI> {

	/** */
	public RequestC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(MessageI.class, fa);

	}

	/* */
	@Override
	public MessageI convert(MessageContext f) {

		return (MessageI) f.getRequest();

	}

}
