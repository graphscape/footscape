/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class BooleanJCC extends JsonCodecCSupport<BooleanData> implements
		CodecI {

	/** */
	public BooleanJCC(FactoryI f) {
		super("b", BooleanData.class, f);
	}

	/* */
	@Override
	protected BooleanData decodeWithOutType(JSONValue js) {

		Boolean v = ((JSONBoolean) js).booleanValue();
		BooleanData rt = BooleanData.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(BooleanData d) {

		return JSONBoolean.getInstance(d.getValue());

	}

}
