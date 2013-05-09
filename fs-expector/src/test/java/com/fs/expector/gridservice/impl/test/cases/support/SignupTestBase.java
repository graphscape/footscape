/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import com.fs.commons.api.client.BClientFactoryI.ProtocolI;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;

/**
 * @author wuzhen
 * 
 */
public class SignupTestBase extends TestBase {

	/**
	 * @param p
	 */
	public SignupTestBase(ProtocolI p) {
		super(p);
	}

	public void doTestSignup() throws Exception {

		String nick = "user1";
		String email = nick + "@domain.com";
		MockExpectorClient client = this.newClient(email, nick);

		String accId = client.getAccountId();
		assertNotNull(accId);
		String sid = client.getSessionId();
		assertNotNull(sid);

	}
}
