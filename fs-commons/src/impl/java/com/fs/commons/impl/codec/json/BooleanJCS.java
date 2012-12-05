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
public class BooleanJCS extends JsonCodecSupport<Boolean, Boolean> implements
		CodecI {

	/** */
	public BooleanJCS(FactoryI f) {
		super("b", Boolean.class, f);
	}

	/* */
	@Override
	protected Boolean decodeWithOutType(Boolean js) {

		return js;
	}

	/* */
	@Override
	protected Boolean encodeWithOutType(Boolean d) {

		return d;

	}

}
