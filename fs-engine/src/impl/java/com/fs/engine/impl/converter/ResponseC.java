/**
 * Jul 22, 2012
 */
package com.fs.engine.impl.converter;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.support.HandleContextConverterSupport;

/**
 * @author wu
 * 
 */
public class ResponseC extends HandleContextConverterSupport<ResponseI> {

	/** */
	public ResponseC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(ResponseI.class, fa);

	}

	/* */
	@Override
	public ResponseI convert(HandleContextI f) {

		return f.getResponse();

	}

}
