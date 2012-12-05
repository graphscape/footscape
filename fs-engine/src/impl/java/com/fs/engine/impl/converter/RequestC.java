/**
 * Jul 22, 2012
 */
package com.fs.engine.impl.converter;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.support.HandleContextConverterSupport;

/**
 * @author wu
 * 
 */
public class RequestC extends HandleContextConverterSupport<RequestI> {

	/** */
	public RequestC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(RequestI.class, fa);

	}

	/* */
	@Override
	public RequestI convert(HandleContextI f) {

		return f.getRequest();

	}

}
