/**
 * Jul 22, 2012
 */
package com.fs.engine.api.support;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.engine.api.HandleContextI;

/**
 * @author wu
 * 
 */
public abstract class HandleContextConverterSupport<T> extends
		ConverterSupport<HandleContextI, T> {

	/** */
	public HandleContextConverterSupport(Class<T> tc, ConverterI.FactoryI fa) {
		super(HandleContextI.class, tc, fa);

	}

}
