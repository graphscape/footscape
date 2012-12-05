/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class StringJCC extends JsonCodecCSupport<StringData> implements CodecI {

	/** */
	public StringJCC(FactoryI f) {
		super("s", StringData.class, f);

	}

	/* */
	@Override
	protected StringData decodeWithOutType(JSONValue jv) {
		JSONString js = (JSONString) jv;
		StringData rt = StringData.valueOf(js.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(StringData d) {

		return new JSONString(d.getValue());

	}

}
