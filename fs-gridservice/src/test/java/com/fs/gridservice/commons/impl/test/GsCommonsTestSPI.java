/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.test;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.impl.test.mock.server.MockAuthProvider;

/**
 * @author wuzhen
 * 
 */
public class GsCommonsTestSPI extends SPISupport {

	/**
	 * @param id
	 */
	public GsCommonsTestSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doActive(com.fs.commons.api.
	 * ActiveContext)
	 */
	@Override
	public void doActive(ActiveContext ac) {
		MockAuthProvider ap = new MockAuthProvider();
		ac.getContainer().addObject(this, "MOCK_AP", ap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.SPISupport#doDeactive(com.fs.commons.api.
	 * ActiveContext)
	 */
	@Override
	protected void doDeactive(ActiveContext ac) {
		// TODO Auto-generated method stub

	}

}
