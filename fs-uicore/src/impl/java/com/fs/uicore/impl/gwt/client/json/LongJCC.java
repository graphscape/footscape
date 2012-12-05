/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.data.basic.LongData;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class LongJCC extends JsonCodecCSupport<LongData> implements CodecI {

	/** */
	public LongJCC(FactoryI f) {
		super("l", LongData.class, f);
	}

	/* */
	@Override
	protected LongData decodeWithOutType(JSONValue js) {
		long v = Long.valueOf(js.toString());
		LongData rt = LongData.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(LongData d) {

		return new JSONNumber(d.getValue());

	}

}
