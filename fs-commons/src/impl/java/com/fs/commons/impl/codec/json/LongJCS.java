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
public class LongJCS extends JsonCodecSupport<Long, Number> implements CodecI {

	/** */
	public LongJCS(FactoryI f) {
		super("l", Long.class, f);
	}

	/* */
	@Override
	protected Long decodeWithOutType(Number js) {

		long v = Long.valueOf(js.toString());
		Long rt = Long.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected Number encodeWithOutType(Long d) {

		return d;

	}

}
