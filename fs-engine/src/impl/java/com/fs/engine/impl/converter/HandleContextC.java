/**
 * Jul 22, 2012
 */
package com.fs.engine.impl.converter;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandleContextConverterSupport;

/**
 * @author wu
 * 
 */
public class HandleContextC extends
		HandleContextConverterSupport<HandleContextI> {

	/** */
	public HandleContextC(com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(HandleContextI.class, fa);

	}

	/* */
	@Override
	public HandleContextI convert(HandleContextI f) {

		return f;

	}
}
