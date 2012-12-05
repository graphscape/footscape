/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class ObjectListJCC extends JsonCodecCSupport<ObjectListData> implements
		CodecI {

	/** */
	public ObjectListJCC(FactoryI f) {
		super("L", ObjectListData.class, f);
	}

	/* */
	@Override
	public ObjectListData decodeWithOutType(JSONValue jv) {
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

		return rt;

	}

	/* */
	@Override
	public JSONValue encodeWithOutType(ObjectListData ud) {

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
