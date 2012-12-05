/**
 *  Nov 23, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.LazyI;

/**
 * @author wuzhen
 * 
 */
public class NoneLazy<T> implements LazyI<T> {

	protected T object;

	public NoneLazy(T obj) {
		this.object = obj;
	}

	@Override
	public T get() {
		return this.object;
	}

}
