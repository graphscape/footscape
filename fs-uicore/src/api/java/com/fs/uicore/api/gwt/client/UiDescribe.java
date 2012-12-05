/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 1, 2012
 */
package com.fs.uicore.api.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiType;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class UiDescribe {

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

	public UiDescribe attribute(String key, Object value) {
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

	public UiDescribe addAll(UiDescribe ad) {
		this.attributeMap.putAll(ad.attributeMap);
		return this;
	}

	public boolean isMatchTo(UiDescribe des) {// this is belongs to
		if (des == null) {
			return true;
		}
		List<Map.Entry<String, Object>> eL = des.entryList();
		for (Map.Entry<String, Object> me : eL) {
			Object o1 = this.getAttribute(me.getKey());
			Object o2 = me.getValue();

			if ((o1 instanceof Class) && (o2 instanceof Class)) {
				throw new UiException("not supported");
			} else if ((o1 instanceof UiType<?>) && (o2 instanceof UiType<?>)) {
				UiType<?> t1 = (UiType<?>) o1;
				UiType<?> t2 = (UiType<?>) o2;
				return t2.isSubType(t1, true);//
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
