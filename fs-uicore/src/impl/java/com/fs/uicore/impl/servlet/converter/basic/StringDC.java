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
public class StringDC extends ConverterSupport<StringData, String> {

	/** */
	public StringDC(ConverterI.FactoryI fa) {
		super(StringData.class, String.class, fa);

	}

	/* */
	@Override
	public String convert(StringData f) {

		return f.getValue();

	}

}
