/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.objects;

import java.util.concurrent.TimeUnit;

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
public class DgQueueHC<T> extends HazelcastObjectWrapper<IQueue<Object>> implements DgQueueI<T> {

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
			Object o = this.target.take();
			return (T) this.decode(o);
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
		this.target.offer(this.encode(t));
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public T poll(long time, TimeUnit tu) {
		//
		try {
			Object obj = this.target.poll(time, tu);
			return (T) this.decode(obj);
		} catch (InterruptedException e) {
			throw new FsException(e);//
		}
	}

}
