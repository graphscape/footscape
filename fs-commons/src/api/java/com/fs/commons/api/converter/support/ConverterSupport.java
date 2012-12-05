/**
 * Jun 23, 2012
 */
package com.fs.commons.api.converter.support;

import com.fs.commons.api.context.ContextI;
import com.fs.commons.api.converter.ConverterI;

/**
 * @author wu
 * 
 */
public abstract class ConverterSupport<F, T> implements ConverterI<F, T> {

	protected ConverterI.FactoryI factory;

	protected Class<F> fromClass;

	protected Class<T> toClass;

	public ConverterSupport(Class<F> fc, Class<T> tc, ConverterI.FactoryI fa) {
		this.factory = fa;
		this.fromClass = fc;
		this.toClass = tc;
	}

	/* */
	@Override
	public Class<F> getSourceClass() {

		return this.fromClass;

	}

	/* */
	@Override
	public Class<T> getTagetClass() {

		return this.toClass;

	}

	@Override
	public T convert(F s, ContextI ctx) {
		return this.convert(s);
	}
}
