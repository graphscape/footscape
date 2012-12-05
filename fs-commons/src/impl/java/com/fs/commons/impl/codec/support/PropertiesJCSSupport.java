/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec.support;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public abstract class PropertiesJCSSupport<T> extends
		JsonCodecSupport<T, JSONObject> implements CodecI {

	/** */
	public PropertiesJCSSupport(String typeCode, Class<T> cls, FactoryI f) {
		super(typeCode, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONObject jv) {
		JSONObject jo = (JSONObject) jv;
		PropertiesI<Object> rt = new MapProperties<Object>();
		for (Object ks : jo.keySet()) {
			String key = (String) ks;
			JSONArray jvX = (JSONArray) jo.get(key);// must be array for any

			Object value = null;
			// data
			if (jvX != null) {

				String type = this.getType(jvX);
				CodecI c = this.factory.getCodec(type);

				value = c.decode(jvX);
			}
			rt.setProperty(key, value);
		}

		return this.convert(rt);

	}

	protected abstract T convert(PropertiesI<Object> pts);

	protected abstract PropertiesI<Object> convert(T t);

	/* */
	@Override
	public JSONObject encodeWithOutType(T ud) {
		PropertiesI<Object> pts = this.convert(ud);
		JSONObject rt = new JSONObject();
		List<String> kL = pts.keyList();

		for (String key : kL) {
			Object da = pts.getProperty(key);// TODO
			JSONArray value = null;
			if (da != null) {
				CodecI c = this.factory.getCodec(da.getClass());

				value = (JSONArray) c.encode(da);
			}
			rt.put(key, value);

		}
		return rt;

	}

}
