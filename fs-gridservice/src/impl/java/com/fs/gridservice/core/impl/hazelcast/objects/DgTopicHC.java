/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.objects;

import com.fs.gridservice.core.api.objects.DgTopicI;
import com.fs.gridservice.core.impl.hazelcast.DataGridHC;
import com.fs.gridservice.core.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgTopicHC<T> extends HazelcastObjectWrapper<ITopic<T>> implements DgTopicI<T> {

	/**
	 * @param q
	 */
	public DgTopicHC(String name, Instance q, DataGridHC dg) {
		super(name, q, dg);
	}

	@Override
	public void dump() {
		this.assertNotDestroied();
		System.out.println("topic:" + this.name);
		System.out.println("-Start------------------------");
		System.out.println("TODO");
		System.out.println("------------------------End-");

	}
}
