/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec.json;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.impl.codec.support.JsonCodecSupport;

/**
 * @author wu
 * 
 */
public class StringJCS extends JsonCodecSupport<String, String> implements
		CodecI {

	/** */
	public StringJCS(FactoryI f) {
		super("s", String.class, f);

	}

	/* */
	@Override
	protected String decodeWithOutType(String jv) {
		return jv;
	}

	/* */
	@Override
	protected String encodeWithOutType(String d) {

		return d;

	}

}
