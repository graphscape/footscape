/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 4, 2012
 */
package com.fs.commons.api.value.support;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public class ProxyPropertiesSupport<T> implements PropertiesI<T> {

	protected PropertiesI<T> target;

	public ProxyPropertiesSupport() {
		this(null);
	}

	public ProxyPropertiesSupport(PropertiesI<T> t) {
		this.target = t;
	}

	@Override
	public void setProperty(String key, T value) {
		//
		this.target.setProperty(key, value);
		//
	}

	@Override
	public void setProperty(Entry<String, T> entry) {
		//
		this.target.setProperty(entry);
		//
	}

	@Override
	public T getProperty(String key) {
		return this.target.getProperty(key);
	}

	@Override
	public T getProperty(String key, boolean force) {
		//
		return this.target.getProperty(key, force);
		//
	}

	@Override
	public boolean getPropertyAsBoolean(String key, boolean def) {
		//
		return this.target.getPropertyAsBoolean(key, def);
		//
	}

	@Override
	public List<String> keyList() {
		//
		return this.target.keyList();
		//
	}

	@Override
	public void setProperties(Map<String, T> map) {
		//
		this.target.setProperties(map);
		//
	}

	@Override
	public void setPropertiesByArray(Object... keyValues) {
		//
		this.target.setPropertiesByArray(keyValues);
		//
	}

	@Override
	public void setProperties(PropertiesI<T> pts) {
		//
		this.target.setProperties(pts);
		//
	}

	@Override
	public Map<String, T> getAsMap() {
		//
		return this.target.getAsMap();
		//
	}

	@Override
	public boolean isContainsSameProperties(Object... kvs) {
		//
		return this.target.isContainsSameProperties(kvs);
		//
	}

	@Override
	public boolean isContainsSameProperties(PropertiesI<T> kvs) {
		//
		return this.target.isContainsSameProperties(kvs);
		//
	}

	/*
	 * Nov 29, 2012
	 */
	@Override
	public PropertiesI<T> convert(String[] from, boolean[] force, String[] to) {
		//
		return this.target.convert(from, force, to);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.value.PropertiesI#getPropertyAsCsv(java.lang.String)
	 */
	@Override
	public List<String> getPropertyAsCsv(String key) {
		// TODO Auto-generated method stub
		return this.target.getPropertyAsCsv(key);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		ProxyPropertiesSupport pps = (ProxyPropertiesSupport) obj;
		return ObjectUtil.nullSafeEquals(this.target, pps.target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.value.PropertiesI#removeProperty(java.lang.String)
	 */
	@Override
	public T removeProperty(String key) {
		// TODO Auto-generated method stub
		return this.target.removeProperty(key);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.value.PropertiesI#mergeFrom(com.fs.commons.api.value
	 * .PropertiesI)
	 */
	@Override
	public PropertiesI<T> mergeFrom(PropertiesI<T> pts) {
		this.target.mergeFrom(pts);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.value.PropertiesI#getProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public T getPropertyWithDefault(String key, T def) {
		return this.target.getPropertyWithDefault(key, def);

	}

}
