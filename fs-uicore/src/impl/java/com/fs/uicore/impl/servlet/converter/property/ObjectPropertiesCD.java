/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.servlet.converter.property;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.converter.support.ConverterSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesCD extends
		ConverterSupport<PropertiesI, ObjectPropertiesData> {

	/** */
	public ObjectPropertiesCD(ConverterI.FactoryI fa) {
		super(PropertiesI.class, ObjectPropertiesData.class, fa);

	}

	/* */
	@Override
	public ObjectPropertiesData convert(PropertiesI t) {
		ObjectPropertiesData rt = new ObjectPropertiesData();

		for (Object okey : t.keyList()) {
			String key = (String) okey;
			Object o = t.getProperty(key);
			if (o == null) {
				continue;
			}
			ConverterI dc = this.factory.getConverter(o.getClass(),
					UiData.class, false);
			UiData value = (UiData) dc.convert(o);
			rt.setProperty(key, value);

		}
		return rt;

	}

}
