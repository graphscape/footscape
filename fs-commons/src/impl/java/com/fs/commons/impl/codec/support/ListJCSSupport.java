/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec.support;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.fs.commons.api.codec.CodecI;

/**
 * @author wu
 * 
 */
public abstract class ListJCSSupport<T> extends JsonCodecSupport<T, JSONArray>
		implements CodecI {

	/** */
	public ListJCSSupport(String typeCode, Class<T> cls, FactoryI f) {
		super(typeCode, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONArray jv) {
		JSONArray jo = (JSONArray) jv;
		List rt = new ArrayList();
		for (int i = 0; i < jo.size(); i++) {

			JSONArray jvX = (JSONArray) jo.get(i);// must be array for any
													// data
			String type = this.getType(jvX);
			CodecI c = this.factory.getCodec(type);

			Object value = c.decode(jvX);
			rt.add(value);
		}

		return this.convert(rt);

	}

	protected abstract T convert(List l);

	protected abstract List convert(T t);

	/* */
	@Override
	public JSONArray encodeWithOutType(T t) {
		List ud = this.convert(t);
		JSONArray rt = new JSONArray();
		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			CodecI c = this.factory.getCodec(da.getClass());

			JSONArray value = (JSONArray) c.encode(da);
			rt.add(value);
		}
		return rt;

	}

}
