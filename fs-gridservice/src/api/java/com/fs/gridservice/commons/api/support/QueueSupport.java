/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.api.support;

import com.fs.gridservice.commons.api.GridedDataI;
import com.fs.gridservice.core.api.objects.DgQueueI;

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
