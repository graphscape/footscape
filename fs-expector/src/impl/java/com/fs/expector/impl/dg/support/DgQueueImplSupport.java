/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.support;

import com.fs.datagrid.api.objects.DgQueueI;

/**
 * @author wuzhen
 * 
 */
public class DgQueueImplSupport<I, T extends I> extends
		DgCollectionSupport<I, T, DgQueueI<I>> {

	/**
	 * @param name
	 */
	public DgQueueImplSupport(Class<I> icls, Class<T> wcls) {
		super(icls, wcls);
		this.wrapperClass = wcls;
	}

	@Override
	protected DgQueueI<I> activeTarget() {
		return this.dg.getQueue(collectionName, this.wrapperItf,
				this.wrapperClass);

	}

}
