/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.impl;

import com.fs.datagrid.api.DataWrapperI;
import com.fs.datagrid.api.DgObjectI;
import com.fs.datagrid.impl.hazelcast.util.WrapperUtil;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperDgObject<V, VW extends DataWrapperI, T extends DgObjectI>
		implements DgObjectI {

	protected T target;

	protected Class<VW> wcls;

	public ProxyWrapperDgObject(T t, Class<VW> wcls) {
		this.target = t;
		this.wcls = wcls;
	}

	protected VW wrap(V v) {
		return WrapperUtil.wrapper(v, wcls);
	}

	public T getTarget() {
		return target;
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
