/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.support;

import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.expector.api.GridedDataI;

/**
 * @author wuzhen
 * 
 */
public class QueueSupport<T extends GridedDataI> extends CollectionSupport<T, DgQueueI<T>> {

	/**
	 * @param name
	 */
	public QueueSupport(Class<T> wcls) {
		super(wcls);
	}

	@Override
	protected DgQueueI<T> activeTarget() {
		return this.dg.getQueue(collectionName, this.wrapperClass);

	}

}
