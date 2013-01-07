/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.json;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.fs.uicore.impl.gwt.client.support.JsonCodecCSupport;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class DateJCC extends JsonCodecCSupport<DateData> implements CodecI<DateData> {

	/** */
	public DateJCC(FactoryI f) {
		super("d", DateData.class, f);
	}

	/* */
	@Override
	protected DateData decodeWithOutType(JSONValue js) {
		if (js instanceof JSONNull) {
			return null;//
		}
		JSONString jsS = (JSONString) js;
		DateData rt = DateUtil.parse(jsS.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(DateData d) {

		String ds = DateUtil.format(d, false);
		return ds == null ? JSONNull.getInstance() : new JSONString(ds);

	}

}
