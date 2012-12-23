/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.core.UiData;

/**
 * @author wu
 * 
 */
public abstract class CodecSupport<T extends UiData, SER> implements CodecI {
	protected CodecI.FactoryI factory;

	protected String typeCode;
	protected Class<? extends UiData> dataClass;

	public CodecSupport(String tc, Class<? extends UiData> dc, CodecI.FactoryI f) {
		this.factory = f;
		this.typeCode = tc;
		this.dataClass = dc;
	}

	/* */
	@Override
	public UiData decode(Object s) {
		SER ser = (SER) s;
		return this.decodeInternal(ser);

	}

	/* */
	@Override
	public SER encode(UiData ud) {

		return this.encodeInternal((T) ud);

	}

	protected abstract T decodeInternal(SER js);

	
	protected abstract SER encodeInternal(T d);

	@Override
	public String getTypeCode() {
		return typeCode;
	}

	@Override
	public Class<? extends UiData> getDataClass() {
		return dataClass;
	}
}
