/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter;

import com.fs.commons.api.converter.support.ConverterFactorySupport;
import com.fs.uicore.impl.servlet.converter.basic.IntegerCD;
import com.fs.uicore.impl.servlet.converter.basic.IntegerDC;
import com.fs.uicore.impl.servlet.converter.basic.StringCD;
import com.fs.uicore.impl.servlet.converter.basic.StringDC;
import com.fs.uicore.impl.servlet.converter.list.ObjectListCD;
import com.fs.uicore.impl.servlet.converter.list.ObjectListDC;
import com.fs.uicore.impl.servlet.converter.property.ObjectPropertiesCD;
import com.fs.uicore.impl.servlet.converter.property.ObjectPropertiesDC;

/**
 * @author wu
 * 
 */
public class DataConverterFactory extends ConverterFactorySupport {

	public DataConverterFactory() {

		this.addConverter(new StringDC(this));
		this.addConverter(new IntegerDC(this));
		this.addConverter(new ObjectPropertiesDC(this));
		this.addConverter(new ObjectListDC(this));
		//
		this.addConverter(new StringCD(this));
		this.addConverter(new IntegerCD(this));
		this.addConverter(new ObjectPropertiesCD(this));
		this.addConverter(new ObjectListCD(this));

	}

}
