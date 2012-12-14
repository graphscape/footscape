/**
 *  Dec 14, 2012
 */
package com.fs.datagrid.impl.proxy;

import com.fs.datagrid.api.DataWrapperI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.datagrid.impl.ProxyWrapperDgObject;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperMap<K, V, VW extends DataWrapperI<V>> extends
		ProxyWrapperDgObject<V, VW, DgMapI<K, V>> implements DgMapI<K, VW> {

	/**
	 * @param t
	 */
	public ProxyWrapperMap(DgMapI<K, V> t, Class<VW> wcls) {
		super(t, wcls);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgMapI#getValue(java.lang.Object)
	 */
	@Override
	public VW getValue(K key) {
		V v = this.target.getValue(key);
		return this.wrap(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.datagrid.api.objects.DgMapI#put(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public VW put(K key, VW value) {

		V rt = this.target.put(key, value == null ? null : value.getTarget());
		return this.wrap(rt);

	}

}
