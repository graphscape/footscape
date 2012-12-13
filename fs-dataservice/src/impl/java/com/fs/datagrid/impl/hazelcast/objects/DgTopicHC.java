/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.objects;

import com.fs.datagrid.api.objects.DgTopicI;
import com.fs.datagrid.impl.hazelcast.DataGridHC;
import com.fs.datagrid.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgTopicHC<T> extends HazelcastObjectWrapper<ITopic<T>> implements
		DgTopicI<T> {

	/**
	 * @param q
	 */
	public DgTopicHC(String name, Instance q, DataGridHC dg) {
		super(name, q, dg);
	}

}
