/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 27, 2013
 */
package com.fs.expector.gridservice.impl.util;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.impl.ExpectorGsSPI;

/**
 * @author wu
 * 
 */
public class I18nUtil {

	public static PropertiesI<String> resolveResource(String locale) {
		PropertiesI<String> rt = loadResource(null);
		if (locale != null) {
			PropertiesI<String> rt2 = loadResource(locale);
			rt.setProperties(rt2);
		}
		return rt;
	}

	public static PropertiesI<String> loadResource(String locale) {
		PropertiesI<String> rt = new MapProperties<String>();

		String id = ExpectorGsSPI.class.getName();
		id += ".I18n";
		if (locale != null) {// default configuration.
			id += "." + locale;
		}
		Configuration cfg = Configuration.properties(id);
		rt.setProperties(cfg);
		return rt;
	}
}
