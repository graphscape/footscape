/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.lang.FsException;
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
public class DgMapHC<K, V> extends HazelcastObjectWrapper<IMap<K, V>> implements DgMapI<K, V> {

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
		MapEntry<K, V> se = this.target.getMapEntry(key);
		if (se == null) {
			return null;
		}
		V rt = se.getValue();

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

	/*
	 * Dec 15, 2012
	 */
	@Override
	public V remove(K key) {
		//
		V v = this.target.remove(key);

		return v;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<K> keyList() {
		//
		Set<K> ks = this.target.keySet();

		return new ArrayList<K>(ks);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<V> valueList() {
		//
		Collection<V> vs = this.target.values();

		return new ArrayList<V>(vs);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public V getValue(K key, boolean force) {
		//
		V rt = this.getValue(key);
		if (rt == null && force) {
			throw new FsException("no value for key:" + key + " in dgmap:" + this.name);
		}
		return rt;
	}

}
