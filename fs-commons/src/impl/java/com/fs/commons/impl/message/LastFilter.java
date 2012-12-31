/**
 *  Dec 28, 2012
 */
package com.fs.commons.impl.message;

import com.fs.commons.api.filter.support.FilterSupport;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;

/**
 * @author wuzhen
 * 
 */
public class LastFilter extends FilterSupport<MessageI, ResponseI> {
	public MessageServiceImpl engineImpl;

	public LastFilter(MessageServiceImpl ei) {
		super();
		this.priority = Integer.MAX_VALUE;//
		this.engineImpl = ei;
	}

	/*
	
	 */
	@Override
	public boolean doFilter(Context<MessageI, ResponseI> chain) {
		engineImpl.lastFilter(chain);
		return true;
	}

}