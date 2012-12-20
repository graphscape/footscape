/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.objects;

import java.util.concurrent.TimeUnit;

import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.gridservice.core.impl.hazelcast.DataGridHC;
import com.fs.gridservice.core.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgQueueHC<T> extends HazelcastObjectWrapper<IQueue<Object>>
		implements DgQueueI<T> {

	/**
	 * @param q
	 */
	public DgQueueHC(String name, Instance q, DataGridHC dg) {
		super(name, (IQueue<T>) q, dg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgQueueI#take()
	 */
	@Override
	public T take() {
		this.assertNotDestroied();
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
	 * @see com.fs.gridservice.core.api.objects.DgQueueI#offer(java.lang.Object)
	 */
	@Override
	public void offer(T t) {
		this.assertNotDestroied();
		this.target.offer(this.encode(t));
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public T poll(long time, TimeUnit tu) {
		//
		this.assertNotDestroied();
		try {
			Object obj = this.target.poll(time, tu);
			return (T) this.decode(obj);
		} catch (InterruptedException e) {
			throw new FsException(e);//
		}
	}

	@Override
	public void dump() {
		this.assertNotDestroied();
		System.out.println("queue:" + this.name);
		System.out.println("-Start------------------------");
		System.out.println("TODO");
		System.out.println("------------------------End-");

	}
}
