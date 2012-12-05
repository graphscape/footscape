/**
 * Jun 19, 2012
 */
package com.fs.commons.api.filter.support;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.filter.FilterI;

/**
 * @author wu
 * 
 */
public abstract class FilterSupport<REQ, RES> extends ConfigurableSupport
		implements FilterI<REQ, RES> {

	protected int priority;

	/** */
	public FilterSupport() {
		super();

	}

	public FilterSupport(int priority) {
		super();
		this.priority = priority;
	}

	/*
	
	 */
	@Override
	public void filter(Context<REQ, RES> fc) {
		boolean stop = this.doFilter(fc);
		if (stop) {
			return;
		}
		FilterI<REQ, RES> f = fc.next(false);
		if (f != null) {
			f.filter(fc);
		}
	}

	@Override
	public int getPriority() {
		//
		return this.priority;
		//
	}

	protected abstract boolean doFilter(Context<REQ, RES> fc);

}
