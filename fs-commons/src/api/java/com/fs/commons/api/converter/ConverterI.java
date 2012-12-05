/**
 * Jun 22, 2012
 */
package com.fs.commons.api.converter;

import com.fs.commons.api.context.ContextI;

/**
 * @author wu
 * 
 */
public interface ConverterI<S, T> {

	public static interface FactoryI {

		public <S, T> void addConverter(ConverterI<S, T> c);

		public <S, T> ConverterI<S, T> getConverter(Class<S> scls, Class<T> cls);

		public <S, T> ConverterI<S, T> getConverter(Class<S> scls,
				boolean strictTarget, Class<T> cls);

		public <S, T> ConverterI<S, T> getConverter(Class<S> scls,
				Class<T> cls, boolean force);

		public <S, T> ConverterI<S, T> getConverter(Class<S> scls,
				boolean strictTarget, Class<T> cls, boolean force);

	}

	public Class<S> getSourceClass();

	public Class<T> getTagetClass();

	public T convert(S f);

	public T convert(S f, ContextI ctx);

}
