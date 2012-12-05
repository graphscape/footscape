/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicore.api.gwt.client.LazyI;

/**
 * @author wu
 * 
 */
public class NullLazy<T> implements LazyI<T> {

	/*
	 * Nov 23, 2012
	 */
	@Override
	public T get() {
		//
		return null;
	}

}
