/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.api.wrapper;

import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.api.value.support.ProxyPropertiesSupport;
import com.fs.datagrid.api.DataWrapperI;

/**
 * @author wuzhen
 * 
 */
public class PropertiesDataWrapper extends ProxyPropertiesSupport<Object> implements
		DataWrapperI<PropertiesI<Object>> {

	public PropertiesDataWrapper() {
		this(new MapProperties<Object>());
	}

	/**
	 * @param t
	 */
	public PropertiesDataWrapper(PropertiesI<Object> t) {
		super(t);
	}

	@Override
	public PropertiesI<Object> getTarget() {
		return this.target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.DataWrapperI#setTarget(java.lang.Object)
	 */
	@Override
	public void setTarget(PropertiesI<Object> t) {
		this.target = t;
	}

	
}
