/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class IntegerJCC extends JsonCodecCSupport<Integer> implements CodecI<Integer> {

	/** */
	public IntegerJCC(FactoryI f) {
		super("i", Integer.class, f);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(JSONValue js) {

		int v = Integer.valueOf(js.toString());
		Integer rt = Integer.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Integer d) {

		return new JSONNumber(d);

	}

}
