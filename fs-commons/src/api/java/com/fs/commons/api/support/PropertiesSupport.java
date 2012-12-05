/**
 * Jun 22, 2012
 */
package com.fs.commons.api.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public abstract class PropertiesSupport<T> implements PropertiesI<T> {
	/* */
	@Override
	public void setProperties(Object... keyValues) {
		int i = 0;
		while (i < keyValues.length) {
			String key = String.valueOf(keyValues[i++]);
			Object value = keyValues[i++];
			T t = (T) value;
			this.setProperty(key, t);
		}
	}

	@Override
	public boolean getPropertyAsBoolean(String key, boolean def) {
		T rt = this.getProperty(key);
		if (rt == null) {
			return def;
		}
		return Boolean.valueOf("" + rt);
	}

	/* */
	@Override
	public void setProperties(PropertiesI<T> pts) {
		this.setProperties(pts.getAsMap());
	}

	/* */
	@Override
	public void setProperties(Map<String, T> map) {
		for (Map.Entry<String, T> e : map.entrySet()) {
			this.setProperty(e);//
		}
	}

	@Override
	public void setProperty(Map.Entry<String, T> entry) {
		this.setProperty(entry.getKey(), entry.getValue());
	}

	/* */
	@Override
	public Map<String, T> getAsMap() {

		Map<String, T> rt = new HashMap<String, T>();
		List<String> kL = this.keyList();
		for (String key : kL) {
			rt.put(key, this.getProperty(key));

		}
		return rt;

	}

	@Override
	public boolean equals(Object kvs) {
		if (kvs == null || !(kvs instanceof PropertiesI)) {
			return false;
		}
		PropertiesI pts = (PropertiesI) kvs;
		List<String> kL = pts.keyList();
		List<String> kL2 = this.keyList();
		if (kL.size() != kL2.size()) {
			return false;
		}
		for (String k : kL) {
			Object o1 = this.getProperty(k);
			Object o2 = pts.getProperty(k);
			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Nov 29, 2012
	 */
	@Override
	public PropertiesI<T> convert(String[] from, boolean[] force, String[] to) {
		//
		PropertiesI<T> rt = new MapProperties<T>();
		for (int i = 0; i < from.length; i++) {
			T value = this.getProperty(from[i], force[i]);
			rt.setProperty(to[i], value);
		}
		return rt;
	}

	@Override
	public boolean isContainsSameProperties(Object... kvs) {
		PropertiesI<T> pts = MapProperties.valueOf(kvs);
		return this.isContainsSameProperties(pts);
	}

	@Override
	public boolean isContainsSameProperties(PropertiesI<T> kvs) {
		List<String> ks1 = kvs.keyList();
		for (String k : ks1) {
			Object o1 = this.getProperty(k);
			Object o2 = kvs.getProperty(k);
			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}
		}
		return true;

	}
}
