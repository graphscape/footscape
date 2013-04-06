/**
 * Jun 24, 2012
 */
package com.fs.uicore.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wu
 * 
 */
public class UiCoreImplTestSPI extends SPISupport {

	/** */
	public UiCoreImplTestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
