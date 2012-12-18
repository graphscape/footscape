/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl.proxy;

import java.util.List;

import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.core.api.WrapperGdI;
import com.fs.gridservice.core.api.objects.DgSetI;
import com.fs.gridservice.core.impl.ProxyWrapperDgObject;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperSet<V, VW extends WrapperGdI<V>> extends
		ProxyWrapperDgObject<V, VW, DgSetI<V>> implements DgSetI<VW> {

	/**
	 * @param t
	 */
	public ProxyWrapperSet(DgSetI<V> t, Class<VW> wcls) {
		super(t, wcls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.core.api.objects.DgSetI#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(VW value) {
		return this.target.contains(this.unwrap(value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(VW value) {
		return this.target.remove(this.unwrap(value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#add(java.lang.Object)
	 */
	@Override
	public boolean add(VW value) {
		return this.target.add(this.unwrap(value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgSetI#valueList()
	 */
	@Override
	public List<VW> valueList() {
		throw new FsException("TODO");
	}

}
