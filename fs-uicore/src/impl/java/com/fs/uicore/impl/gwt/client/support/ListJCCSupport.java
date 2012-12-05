/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.support;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public abstract class ListJCCSupport<T extends UiData> extends
		JsonCodecCSupport<T> implements CodecI {

	/** */
	public ListJCCSupport(String type, Class<T> cls, FactoryI f) {
		super(type, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONValue jv) {
		JSONArray jo = (JSONArray) jv;
		ObjectListData rt = new ObjectListData();
		for (int i = 0; i < jo.size(); i++) {
			JSONArray jvX = (JSONArray) jo.get(i);// must be array for any
													// data
			String type = this.getType(jvX);
			CodecI c = this.factory.getCodec(type);

			UiData value = c.decode(jvX);

			rt.add(value);

		}

		return convert(rt);

	}

	protected abstract T convert(ObjectListData l);

	protected abstract ObjectListData convert(T t);

	/* */
	@Override
	public JSONValue encodeWithOutType(T t) {
		ObjectListData ud = this.convert(t);
		JSONArray rt = new JSONArray();

		for (int i = 0; i < ud.size(); i++) {
			UiData da = ud.get(i);
			CodecI c = this.factory.getCodec(da.getClass());

			JSONArray value = (JSONArray) c.encode(da);
			rt.set(i, value);
		}
		return rt;

	}

}
