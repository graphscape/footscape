/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.core.api.objects.DgMapI;
import com.fs.gridservice.core.impl.hazelcast.DataGridHC;
import com.fs.gridservice.core.impl.hazelcast.HazelcastObjectWrapper;
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
	 * @see
	 * com.fs.gridservice.core.api.objects.DgMapI#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		this.assertNotDestroied();
		MapEntry<K, V> se = this.target.getMapEntry(key);
		if (se == null) {
			return null;
		}
		V rt = se.getValue();

		return rt;
	}

	@Override
	public V put(K key, V value) {
		this.assertNotDestroied();
		return this.target.put(key, value);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public V remove(K key) {
		//
		this.assertNotDestroied();
		V v = this.target.remove(key);

		return v;
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<K> keyList() {
		//
		this.assertNotDestroied();
		Set<K> ks = this.target.keySet();

		return new ArrayList<K>(ks);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<V> valueList() {
		//
		this.assertNotDestroied();
		Collection<V> vs = this.target.values();

		return new ArrayList<V>(vs);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public V getValue(K key, boolean force) {
		//
		this.assertNotDestroied();
		V rt = this.getValue(key);
		if (rt == null && force) {
			throw new FsException("no value for key:" + key + " in dgmap:" + this.name);
		}
		return rt;
	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public void dump() {
		this.assertNotDestroied();
		System.out.println("map:"+this.name);
		System.out.println("-Start------------------------");
		
		for (K k : this.keyList()) {
			V v = this.getValue(k);
			System.out.println(k + "\t=\t" + v);
		}
		System.out.println("------------------------End-");
		
	}

}
