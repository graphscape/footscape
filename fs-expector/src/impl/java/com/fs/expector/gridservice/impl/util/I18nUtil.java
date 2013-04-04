/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 27, 2013
 */
package com.fs.expector.gridservice.impl.util;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.impl.ExpectorGsSPI;

/**
 * @author wu
 * 
 */
public class I18nUtil {

	private static Map<String, PropertiesI<String>> resCache = new HashMap<String, PropertiesI<String>>();

	public static String localized(String locale, String key) {
		PropertiesI<String> pts = resolveResource(locale);

		String rt = pts.getProperty(key);
		
		return rt == null ? key : rt;
	}

	public static PropertiesI<String> resolveResource(String locale) {
		PropertiesI<String> rt = resCache.get(locale);
		if (rt != null) {
			return rt;
		}
		rt = loadResource(null);
		if (locale != null) {
			PropertiesI<String> rt2 = loadResource(locale);
			rt.setProperties(rt2);
		}
		resCache.put(locale, rt);
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
