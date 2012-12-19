/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.fs.gridservice.core.api.DgObjectI;
import com.fs.gridservice.core.api.WrapperGdI;
import com.fs.gridservice.core.impl.hazelcast.util.WrapperUtil;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperDgObject<V, VW extends WrapperGdI<V>, T extends DgObjectI> implements DgObjectI {

	protected T target;

	protected Class<VW> wcls;

	public ProxyWrapperDgObject(T t, Class<VW> wcls) {
		this.target = t;
		this.wcls = wcls;
	}

	protected V unwrap(VW w) {
		return WrapperUtil.unwrap(w, wcls);
	}

	protected VW wrap(V v) {
		return WrapperUtil.wrapper(v, wcls);
	}

	protected List<VW> wrap(List<V> vl) {
		List<VW> rt = new ArrayList<VW>();
		for (V v : vl) {
			VW w = WrapperUtil.wrapper(v, wcls);
			rt.add(w);
		}
		return rt;

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
	 * @see com.fs.gridservice.core.api.DgObjectI#destroy()
	 */
	@Override
	public void destroy() {
		this.target.destroy();
	}

	@Override
	public void dump() {
		this.target.dump();
	}
}
