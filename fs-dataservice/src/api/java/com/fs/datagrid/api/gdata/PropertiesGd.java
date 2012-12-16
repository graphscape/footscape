/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.api.gdata;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.api.value.support.ProxyPropertiesSupport;
import com.fs.datagrid.api.WrapperGdI;

/**
 * @author wuzhen
 * 
 */
public class PropertiesGd extends ProxyPropertiesSupport<Object> implements
		WrapperGdI<PropertiesI<Object>> {

	public PropertiesGd() {
		this(new MapProperties<Object>());
	}

	/**
	 * @param t
	 */
	public PropertiesGd(PropertiesI<Object> t) {
		super(t);
	}

	@Override
	public PropertiesI<Object> getTarget() {
		return this.target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.WrapperGdI#setTarget(java.lang.Object)
	 */
	@Override
	public void setTarget(PropertiesI<Object> t) {
		this.target = t;
	}

	
}
