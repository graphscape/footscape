/**
 * Jun 19, 2012
 */
package com.fs.commons.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wuzhen
 * 
 */
public class TestSPI extends SPISupport {

	/** */
	public TestSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ContainerI c = ac.getContainer();

	}

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
