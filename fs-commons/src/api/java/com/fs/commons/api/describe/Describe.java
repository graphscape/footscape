/**
 * Jun 20, 2012
 */
package com.fs.commons.api.describe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wuzhen
 * 
 */
public class Describe {

	private Map<String, Object> attributeMap = new HashMap<String, Object>();

	public List<Map.Entry<String, Object>> entryList() {
		List<Map.Entry<String, Object>> rt = new ArrayList<Map.Entry<String, Object>>();
		rt.addAll(this.attributeMap.entrySet());
		return rt;
	}

	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.attributeMap.keySet());
		return rt;
	}

	public Describe attribute(String key, Object value) {
		this.attributeMap.put(key, value);
		return this;
	}

	public Object getAttribute(String key) {

		return this.attributeMap.get(key);
	}

	public <T> T getAttribute(Class<T> cls, String key) {
		Object o = this.getAttribute(key);
		return cls.cast(o);
	}

	public Describe addAll(Describe ad) {
		this.attributeMap.putAll(ad.attributeMap);
		return this;
	}

	public boolean isMatchTo(Describe des) {// this is belongs to
		if (des == null) {
			return true;
		}
		List<Map.Entry<String, Object>> eL = des.entryList();
		for (Map.Entry<String, Object> me : eL) {
			Object o1 = this.getAttribute(me.getKey());
			Object o2 = me.getValue();

			if ((o1 instanceof Class) && (o2 instanceof Class)) {
				Class d1 = (Class) o1;
				Class d2 = (Class) o2;
				if (!d2.isAssignableFrom(d1)) {// this class is sub class of d2
					return false;
				}
			} else {
				if (!ObjectUtil.nullSafeEquals(o1, o2)) {// TODO describe
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return this.attributeMap.toString();
	}
}
