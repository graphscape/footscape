/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.support;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.support.CodecSupport;

/**
 * @author wu
 * 
 */
public abstract class JsonCodecSSupport<T extends UiData, JV> extends
		CodecSupport<T, JSONArray> {

	/** */
	public JsonCodecSSupport(String tc, Class<T> cls, FactoryI f) {
		super(tc, cls, f);
	}

	/* */
	@Override
	protected T decodeInternal(JSONArray js) {

		JV jv = (JV) js.get(1);

		return this.decodeWithOutType(jv);

	}

	protected String getType(JSONArray js) {
		String jt = (String) js.get(0);
		String rt = jt;
		return rt;
	}

	/* */
	@Override
	protected JSONArray encodeInternal(T d) {

		JSONArray rt = new JSONArray();

		rt.add(this.typeCode);
		JV jv = this.encodeWithOutType(d);
		rt.add(jv);
		return rt;

	}

	protected abstract T decodeWithOutType(JV jv);

	protected abstract JV encodeWithOutType(T t);

}
