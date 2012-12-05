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
public class IntegerDC extends ConverterSupport<IntegerData, Integer> {

	/** */
	public IntegerDC(ConverterI.FactoryI fa) {
		super(IntegerData.class, Integer.class, fa);

	}

	/* */
	@Override
	public Integer convert(IntegerData f) {

		return f.getValue();

	}

}
