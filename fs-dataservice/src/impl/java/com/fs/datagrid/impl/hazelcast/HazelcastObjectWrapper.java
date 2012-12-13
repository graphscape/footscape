/**
 *  Dec 13, 2012
 */
package com.fs.datagrid.impl.hazelcast;

import com.fs.datagrid.api.DgObjectI;
import com.hazelcast.core.Instance;

/**
 * @author wuzhen
 * 
 */
public abstract class HazelcastObjectWrapper<T extends Instance> implements DgObjectI {

	protected T target;

	protected String name;

	protected DataGridHC dg;

	protected boolean detroyed;

	public HazelcastObjectWrapper(String name, Instance target, DataGridHC dg) {
		this.name = name;
		this.target = (T) target;
		this.dg = dg;
	}

	public T getTarget() {
		return target;
	}

	@Override
	public void destroy() {

		this.dg.remove(this);
		//
		this.target = null;
		this.name = null;
		this.dg = null;
		this.detroyed = true;

	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Dec 13, 2012
	 */
	protected Object encode(Object t) {
		//
		return this.dg.encode(t);
	}

	/**
	 * Dec 13, 2012
	 */
	protected Object decode(Object o) {
		//
		return this.dg.decode(o);
	}

}
