/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter.basic;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;

/**
 * @author wu
 * 
 */
public class IntegerCD extends ConverterSupport<Integer, IntegerData> {

	/** */
	public IntegerCD(ConverterI.FactoryI fa) {
		super(Integer.class, IntegerData.class, fa);

	}

	/* */
	@Override
	public IntegerData convert(Integer f) {

		return IntegerData.valueOf(f);

	}

}
