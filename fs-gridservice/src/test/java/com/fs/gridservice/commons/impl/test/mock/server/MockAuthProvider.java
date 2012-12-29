/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.test.mock.server;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.session.AuthProviderI;

/**
 * @author wuzhen
 * 
 */
public class MockAuthProvider implements AuthProviderI {

	/*
	 * Dec 29, 2012
	 */
	@Override
	public PropertiesI<Object> auth(PropertiesI<Object> credential) {
		//
		
		return credential;
	}

}
