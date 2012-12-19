/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl;

import com.fs.gridservice.core.api.DgObjectI;

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
	 * @see com.fs.gridservice.core.api.DgObjectI#destroy()
	 */
	@Override
	public void destroy() {
		this.target.destroy();
	}

	/*
	 *Dec 19, 2012
	 */
	@Override
	public void dump() {
		this.target.dump();
	}
}
