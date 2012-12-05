/**
 * Jun 23, 2012
 */
package com.fs.commons.api.codec.support;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.value.ValueI;

/**
 * @author wu
 * 
 */
public abstract class CodecSupport<T, SER> implements CodecI {
	protected CodecI.FactoryI factory;

	protected String typeCode;

	protected Class<?> dataClass;

	public CodecSupport(String tc, Class<?> dc, CodecI.FactoryI f) {
		this.factory = f;
		this.typeCode = tc;
		this.dataClass = dc;
	}

	/* */
	@Override
	public T decode(Object s) {
		SER ser = (SER) s;
		return this.decodeInternal(ser);

	}

	/*
	
	 */
	@Override
	public Object encode(Object ud) {

		return this.encodeInternal((T) ud);
	}

	protected abstract T decodeInternal(SER js);

	protected abstract SER encodeInternal(T d);

	@Override
	public String getTypeCode() {
		return typeCode;
	}

	@Override
	public Class<?> getDataClass() {
		return dataClass;
	}

}
