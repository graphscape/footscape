/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * 
 */
public class CodecFactorySupport implements CodecI.FactoryI {

	private Map<String, CodecI> jcMap = new HashMap<String, CodecI>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public CodecFactorySupport() {

	}

	protected void add(CodecI cd) {
		String tc = cd.getTypeCode();
		Class cls = cd.getDataClass();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new UiException("duplicated:" + cls + ",oldx:" + oldx);
		}
		CodecI old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new UiException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public <T> CodecI<T> getCodec(Class<T> dataCls) {
		String tc = this.classMap.get(dataCls);
		if (tc == null) {
			throw new UiException("no codec found for data class:" + dataCls);
		}
		return this.getCodec(tc);

	}

	/* */
	@Override
	public <T> CodecI<T> getCodec(String type) {

		CodecI rt = this.jcMap.get(type);
		if (rt == null) {
			throw new UiException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
