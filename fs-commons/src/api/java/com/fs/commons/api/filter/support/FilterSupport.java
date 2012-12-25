/**
 * Jun 19, 2012
 */
package com.fs.commons.api.filter.support;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.filter.FilterI;
import com.fs.commons.api.lang.FsException;

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

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);

		int pre = this.config.getPropertyAsInt("priority", 0);
		if (pre == 0) {
			if (this.priority == 0) {
				throw new FsException(
						"filter's priority should not be zero:0 from config:"
								+ cfg);
			}
		} else {
			// override by config
			this.priority = pre;
		}
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
