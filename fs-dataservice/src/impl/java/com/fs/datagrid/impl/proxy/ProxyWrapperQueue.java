/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.impl.proxy;

import com.fs.datagrid.api.DataWrapperI;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.datagrid.impl.ProxyWrapperDgObject;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperQueue<K, V, VW extends DataWrapperI<V>> extends
		ProxyWrapperDgObject<V, VW, DgQueueI<V>> implements DgQueueI<VW> {

	/**
	 * @param t
	 */
	public ProxyWrapperQueue(DgQueueI<V> t, Class<VW> wcls) {
		super(t, wcls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgQueueI#take()
	 */
	@Override
	public VW take() {
		V v = this.target.take();
		return this.wrap(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgQueueI#offer(java.lang.Object)
	 */
	@Override
	public void offer(VW t) {
		V v = t == null ? null : t.getTarget();
		this.target.offer(v);
	}
}
