/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wuzhen
 * 
 */
public class ExpectorSPI extends SPISupport {

	/**
	 * @param id
	 */
	public ExpectorSPI(String id) {
		super(id);
	}

	@Override
	public void doActive(ActiveContext ac) {

		ac.active("sessionManager");
		ac.active("goManager");
		ac.active("eventQueue");
	}

	@Override
	protected void doDeactive(ActiveContext ac) {

	}

}
