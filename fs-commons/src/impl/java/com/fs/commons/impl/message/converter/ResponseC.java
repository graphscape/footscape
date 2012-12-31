/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.message.converter;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.support.ServiceContextConverterSupport;

/**
 * @author wu
 * 
 */
public class ResponseC extends ServiceContextConverterSupport<ResponseI> {

	/** */
	public ResponseC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(ResponseI.class, fa);

	}

	/* */
	@Override
	public ResponseI convert(MessageContext f) {

		return f.getResponse();

	}

}
