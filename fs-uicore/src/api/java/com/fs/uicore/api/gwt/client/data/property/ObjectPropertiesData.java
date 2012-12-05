/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.data.property;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesData extends PropertiesData<UiData> {
	public void setString(String key, String value) {
		this.setProperty(key, StringData.valueOf(value));
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ObjectPropertiesData)) {
			return false;
		}

		return super.isEquals((ObjectPropertiesData) obj);

	}

}
