/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter.basic;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class StringCD extends ConverterSupport<String, StringData> {

	/** */
	public StringCD(ConverterI.FactoryI fa) {
		super(String.class, StringData.class, fa);

	}

	/* */
	@Override
	public StringData convert(String t) {

		return StringData.valueOf(t);

	}

}
