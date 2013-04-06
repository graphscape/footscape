/**
 * Jul 22, 2012
 */
package com.fs.gridservice.commons.impl.converter;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.support.MessageContextConverterSupport;
import com.fs.gridservice.commons.api.EventWrapper;
import com.fs.gridservice.commons.api.data.EventGd;

/**
 * @author wu
 * 
 */
public class EventWrapperC<T extends EventWrapper> extends
		MessageContextConverterSupport<T> {

	/** */
	public EventWrapperC(Class<T> cls,
			com.fs.commons.api.converter.ConverterI.FactoryI fa) {
		super(cls, fa);
	}

	/* */
	@Override
	public T convert(MessageContext f) {

		//the request must is a EventGd which is dispatched by the EventDispatcherSupport,
		EventGd evt = (EventGd) f.getRequest();
		evt.getType();// //TODO check type
		EventWrapper rt = ClassUtil.newInstance(this.toClass,
				new Class[] { EventGd.class }, new Object[] { evt });
		return (T) rt;

	}
}
