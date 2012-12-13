/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.objects;

import com.fs.commons.api.lang.FsException;
import com.fs.datagrid.api.objects.DgQueueI;
import com.fs.datagrid.impl.hazelcast.DataGridHC;
import com.fs.datagrid.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgQueueHC<T> extends HazelcastObjectWrapper<IQueue<T>> implements
		DgQueueI<T> {

	/**
	 * @param q
	 */
	public DgQueueHC(String name, Instance q, DataGridHC dg) {
		super(name, (IQueue<T>) q, dg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgQueueI#take()
	 */
	@Override
	public T take() {
		// TODO Auto-generated method stub
		try {
			return this.target.take();
		} catch (InterruptedException e) {
			throw new FsException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgQueueI#offer(java.lang.Object)
	 */
	@Override
	public void offer(T t) {
		this.target.offer(t);
	}

}
