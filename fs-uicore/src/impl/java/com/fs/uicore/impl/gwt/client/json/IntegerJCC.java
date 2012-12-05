/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class IntegerJCC extends JsonCodecCSupport<IntegerData> implements CodecI {

	/** */
	public IntegerJCC(FactoryI f) {
		super("i", IntegerData.class, f);
	}

	/* */
	@Override
	protected IntegerData decodeWithOutType(JSONValue js) {

		int v = Integer.valueOf(js.toString());
		IntegerData rt = IntegerData.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(IntegerData d) {

		return new JSONNumber(d.getValue());

	}

}
