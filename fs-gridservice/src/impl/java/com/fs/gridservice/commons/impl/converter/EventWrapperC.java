/**
 * Jul 22, 2012
 */
package com.fs.gridservice.commons.impl.converter;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.support.HandleContextConverterSupport;
import com.fs.gridservice.commons.api.EventWrapper;
import com.fs.gridservice.commons.api.data.EventGd;

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

		EventGd evt = (EventGd) f.getRequest().getPayload();
		EventWrapper rt = ClassUtil.newInstance(this.toClass, new Class[] { EventGd.class },
				new Object[] { evt });
		return (T) rt;

	}
}
