/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec.json;

import java.util.Date;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.util.DateUtil;
import com.fs.commons.impl.codec.support.JsonCodecSupport;

/**
 * @author wu
 * 
 */
public class DateJCS extends JsonCodecSupport<Date, String> implements CodecI {

	/** */
	public DateJCS(FactoryI f) {
		super("d", Date.class, f);
	}

	/* */
	@Override
	protected Date decodeWithOutType(String js) {

		return DateUtil.parse(js);
	}

	/* */
	@Override
	protected String encodeWithOutType(Date d) {

		return DateUtil.format(d);

	}

}
