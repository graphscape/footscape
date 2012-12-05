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
public class ObjectPropertiesDC extends
		ConverterSupport<ObjectPropertiesData, PropertiesI> {

	/** */
	public ObjectPropertiesDC(ConverterI.FactoryI fa) {
		super(ObjectPropertiesData.class, PropertiesI.class, fa);

	}

	/* */
	@Override
	public PropertiesI<Object> convert(ObjectPropertiesData f) {
		PropertiesI<Object> rt = new MapProperties<Object>();
		for (String key : f.keyList()) {
			UiData da = f.getProperty(key);
			if (da == null) {
				continue;
			}
			ConverterI dc = this.factory.getConverter(da.getClass(),
					Object.class, false);
			Object value = dc.convert(da);
			rt.setProperty(key, value);
		}
		return rt;

	}

}
