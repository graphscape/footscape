/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.api.core.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wu
 * 
 */
public class PropertiesWrapper<O, T extends PropertiesI<O>> {

	protected T target;

	public <X extends PropertiesWrapper<O, T>> X attachTo(T t) {
		this.target = t;
		return (X) this;
	}

	public PropertiesWrapper<O, T> applyFrom(PropertiesWrapper<O, T>... pwss) {
		for (PropertiesWrapper<O, T> pws : pwss) {
			this.applyFrom(pws.target);
		}
		return this;
	}

	public PropertiesWrapper<O, T> applyFrom(T... ptss) {

		for (T pts : ptss) {
			this.target.setProperties(pts);//
		}

		return this;
	}

	public PropertiesI<O> convert() {
		//
		PropertiesI<O> rt = new MapProperties<O>();
		rt.setProperties(this.target);
		return rt;
	}

	public PropertiesI<O> convert(String[] from, boolean[] force, String[] to) {
		//
		PropertiesI<O> rt = new MapProperties<O>();
		for (int i = 0; i < from.length; i++) {
			O value = this.getProperty(from[i], force[i]);
			rt.setProperty(to[i], value);
		}
		return rt;
	}

	public O getProperty(String key, boolean force) {
		return this.target.getProperty(key, force);
	}

	public void setProperty(String key, O value) {
		this.target.setProperty(key, value);
	}

	/**
	 * @return the target
	 */
	public T getTarget() {
		return target;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		NodeWrapper nw = (NodeWrapper) obj;
		return ObjectUtil.nullSafeEquals(this.target, nw.target);
	}
}
