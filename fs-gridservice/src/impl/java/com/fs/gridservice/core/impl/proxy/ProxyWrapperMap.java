/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.core.impl.proxy;

import java.util.List;

import com.fs.commons.api.HasIdI;
import com.fs.commons.api.lang.FsException;
import com.fs.gridservice.core.api.WrapperGdI;
import com.fs.gridservice.core.api.objects.DgMapI;
import com.fs.gridservice.core.impl.ProxyWrapperDgObject;

/**
 * @author wuzhen
 * 
 */
public class ProxyWrapperMap<K, V, VW extends WrapperGdI<V>> extends
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
	 * @see
	 * com.fs.gridservice.core.api.objects.DgMapI#getValue(java.lang.Object)
	 */
	@Override
	public VW getValue(K key) {
		V v = this.target.getValue(key);
		return this.wrap(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.objects.DgMapI#put(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public VW put(K key, VW value) {

		V rt = this.target.put(key, value == null ? null : value.getTarget());
		return this.wrap(rt);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public VW remove(K key) {
		//
		V rt = this.target.remove(key);
		return this.wrap(rt);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<K> keyList() {
		//
		return this.target.keyList();
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public List<VW> valueList() {
		//
		List<V> rt = this.target.valueList();
		return this.wrap(rt);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public VW getValue(K key, boolean force) {
		//
		return this.wrap(this.target.getValue(key, force));

	}

	public VW put(VW value) {
		if (value == null || !(value instanceof HasIdI)) {
			throw new FsException("please call put(id,value)");
		}
		String id = ((HasIdI) value).getId();
		if (id == null) {
			throw new FsException("id is null for value:" + value);
		}
		return this.put((K) id, value);
	}
}
