/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.api.mock;

import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.api.mock.ProxyMockClient;

/**
 * @author wuzhen
 * 
 */
public abstract class MockExpectorClient extends ProxyMockClient {

	/**
	 * @param c
	 */
	public MockExpectorClient(MockClient mc) {
		super(mc);
	}

	public abstract String signup(String email, String nick);
	
}
