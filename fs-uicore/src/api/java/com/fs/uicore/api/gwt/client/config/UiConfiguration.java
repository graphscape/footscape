/**
 * Sep 21, 2012
 */
package com.fs.uicore.api.gwt.client.config;

import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * 
 * @author wuzhen
 * 
 */
public class UiConfiguration extends PropertiesData<String> {

	public static UiConfiguration valueOf(PropertiesData<String> pds) {
		UiConfiguration rt = new UiConfiguration();
		rt.setProperties(pds);
		return rt;
	}

	public int getAsInt(String key, int def) {
		String rt = this.getProperty(key);

		if (rt == null) {
			return def;
		}
		return Integer.parseInt(rt);
	}

}
