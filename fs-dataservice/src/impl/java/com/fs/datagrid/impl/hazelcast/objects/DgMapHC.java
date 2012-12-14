/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.objects;

import com.fs.datagrid.api.objects.DgMapI;
import com.fs.datagrid.impl.hazelcast.DataGridHC;
import com.fs.datagrid.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Instance;
import com.hazelcast.core.MapEntry;

/**
 * @author wuzhen
 * 
 */
public class DgMapHC<K, V> extends HazelcastObjectWrapper<IMap<K, V>> implements
		DgMapI<K, V> {

	/**
	 * @param q
	 */
	public DgMapHC(String name, Instance q, DataGridHC dg) {

		super(name, (IMap<K, V>) q, dg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgMapI#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		MapEntry<K, ?> se = this.target.getMapEntry(key);
		Object s = se.getValue();
		V rt = this.decode(s);

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgMapI#put(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		return this.target.put(key, value);
	}

}
