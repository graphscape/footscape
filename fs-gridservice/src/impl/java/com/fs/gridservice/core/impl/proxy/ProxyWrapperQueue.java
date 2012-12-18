/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl.proxy;

import java.util.concurrent.TimeUnit;

import com.fs.gridservice.core.api.WrapperGdI;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.gridservice.core.impl.ProxyWrapperDgObject;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperQueue<K, V, VW extends WrapperGdI<V>> extends
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
	 * @see com.fs.gridservice.core.api.objects.DgQueueI#take()
	 */
	@Override
	public VW take() {
		V v = this.target.take();
		return this.wrap(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgQueueI#offer(java.lang.Object)
	 */
	@Override
	public void offer(VW t) {
		V v = t == null ? null : t.getTarget();
		this.target.offer(v);
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public VW poll(long time, TimeUnit tu) {
		//
		V v = this.target.poll(time, tu);
		return this.wrap(v);
	}
}
