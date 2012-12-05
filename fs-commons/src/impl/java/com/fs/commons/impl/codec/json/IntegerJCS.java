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
public class IntegerJCS extends JsonCodecSupport<Integer, Number> implements
		CodecI {

	/** */
	public IntegerJCS(FactoryI f) {
		super("i", Integer.class, f);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(Number js) {

		int v = Integer.valueOf(js.toString());
		Integer rt = Integer.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected Number encodeWithOutType(Integer d) {

		return d;

	}

}
