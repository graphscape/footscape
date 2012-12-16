/**
 * Jul 22, 2012
 */
package com.fs.expector.impl.converter;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandleContextConverterSupport;
import com.fs.expector.api.EventWrapper;

/**
 * @author wu
 * 
 */
public class EventWrapperC<T extends EventWrapper> extends HandleContextConverterSupport<T> {

	/** */
	public EventWrapperC(Class<T> cls, com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(cls, fa);
	}

	/* */
	@Override
	public T convert(HandleContextI f) {

		return (T) f.getRequest().getPayload();

	}
}
