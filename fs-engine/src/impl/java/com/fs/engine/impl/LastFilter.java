/**
 *  Dec 28, 2012
 */
package com.fs.engine.impl;

import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;

/**
 * @author wuzhen
 * 
 */
public class LastFilter extends FilterSupport<RequestI, ResponseI> {
	public EngineImpl engineImpl;

	public LastFilter(EngineImpl ei) {
		super();
		this.priority = Integer.MAX_VALUE;//
		this.engineImpl = ei;
	}

	/*
	
	 */
	@Override
	public boolean doFilter(Context<RequestI, ResponseI> chain) {
		engineImpl.lastFilter(chain);
		return true;
	}

}