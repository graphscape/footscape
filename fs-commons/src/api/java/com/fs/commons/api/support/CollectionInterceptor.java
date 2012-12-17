/**
 *  Dec 17, 2012
 */
package com.fs.commons.api.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ActivableI;
import com.fs.commons.api.InterceptorI;

/**
 * @author wuzhen
 * 
 */
public class CollectionInterceptor implements InterceptorI {

	private List<InterceptorI> interceptors = new ArrayList<InterceptorI>();;

	public void addInterceptor(InterceptorI i) {
		this.interceptors.add(i);
	}

	@Override
	public void beforeActive(ActivableI obj) {
		for (InterceptorI ii : this.interceptors) {
			ii.beforeActive(obj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.InterceptorI#afterActive(com.fs.commons.api.ActivableI
	 * )
	 */
	@Override
	public void afterActive(ActivableI obj) {
		for (InterceptorI ii : this.interceptors) {
			ii.afterActive(obj);
		}
	}

}
