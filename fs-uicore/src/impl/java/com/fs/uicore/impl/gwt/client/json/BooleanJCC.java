/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class BooleanJCC extends JsonCodecCSupport<Boolean> implements CodecI<Boolean> {

	/** */
	public BooleanJCC(FactoryI f) {
		super("b", Boolean.class, f);
	}

	/* */
	@Override
	protected Boolean decodeWithOutType(JSONValue js) {

		Boolean v = ((JSONBoolean) js).booleanValue();
		Boolean rt = (v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Boolean d) {

		return JSONBoolean.getInstance(d);

	}

}
