/**
 *  Dec 19, 2012
 */
package com.fs.uicommons.impl.test;

import com.fs.gridservice.commons.api.session.AuthProviderI;

/**
 * @author wuzhen
 * 
 */
public class MockAuthProvider implements AuthProviderI {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.session.AuthProviderI#auth(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public boolean auth(String accId, String pass) {
		return true;
	}

}
