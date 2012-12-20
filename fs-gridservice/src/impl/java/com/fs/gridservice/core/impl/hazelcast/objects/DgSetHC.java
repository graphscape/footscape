/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.objects;

import java.util.ArrayList;
import java.util.List;

import com.fs.gridservice.core.api.objects.DgSetI;
import com.fs.gridservice.core.impl.hazelcast.DataGridHC;
import com.fs.gridservice.core.impl.hazelcast.HazelcastObjectWrapper;
import com.hazelcast.core.ISet;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public class DgSetHC<V> extends HazelcastObjectWrapper<ISet<V>> implements
		DgSetI<V> {

	/**
	 * @param q
	 */
	public DgSetHC(String name, Instance q, DataGridHC dg) {

		super(name, (ISet<V>) q, dg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.core.api.objects.DgSetI#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(V value) {
		this.assertNotDestroied();
		return this.target.contains(value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(V value) {
		return this.target.remove(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#add(java.lang.Object)
	 */
	@Override
	public boolean add(V value) {
		return this.target.add(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#valueList()
	 */
	@Override
	public List<V> valueList() {

		return new ArrayList<V>(this.target);
	}
	@Override
	public void dump() {
		System.out.println("set:" + this.name);
		System.out.println("-Start------------------------");
		System.out.println("TODO");
		System.out.println("------------------------End-");

	}
}
