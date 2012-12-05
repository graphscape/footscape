/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client.data.property;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class StringPropertiesData extends PropertiesData<String> {
	/* */

	public static StringPropertiesData valueOf(PropertiesData<UiData> pts) {
		StringPropertiesData rt = new StringPropertiesData();

		for (String key : pts.keyList()) {
			UiData di = pts.getProperty(key);
			if (di instanceof StringData) {
				String svalue = ((StringData) di).getValue();
				rt.setProperty(key, svalue);

			} else {
				throw new UiException("cannot convert:" + di + " to:"
						+ StringData.class);
			}
		}
		return rt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof StringPropertiesData)) {
			return false;
		}

		return super.isEquals((StringPropertiesData) obj);

	}

	public void setPropertyAsBoolean(String key, Boolean value) {
		this.setProperty(key, Boolean.toString(value));
	}

	public Boolean getPropertyAsBoolean(String key, boolean def) {
		String rtS = this.getProperty(key);
		return "true".equalsIgnoreCase(rtS) || "Y".equalsIgnoreCase(rtS)
				|| "YES".equalsIgnoreCase(rtS);
	}
}
