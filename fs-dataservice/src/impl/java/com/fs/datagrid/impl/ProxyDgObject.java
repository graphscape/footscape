/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.impl;

import com.fs.datagrid.api.DgObjectI;

/**
 * @author wuzhen
 * 
 */
public class ProxyDgObject<T extends DgObjectI> implements DgObjectI {

	protected T target;

	public T getTarget() {
		return target;
	}

	public ProxyDgObject(T t) {
		this.target = t;
	}

	@Override
	public String getName() {
		return this.target.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.DgObjectI#destroy()
	 */
	@Override
	public void destroy() {
		this.target.destroy();
	}
}
