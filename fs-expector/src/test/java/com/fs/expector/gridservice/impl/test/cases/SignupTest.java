/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends TestBase {

	public void testSignup() throws Exception {

		String nick = "user1";
		String email = nick + "@domain.com";
		MockExpectorClient client = this.newClient(email, nick);

		String accId = client.getAccountId();
		assertNotNull(accId);
		String sid = client.getSessionId();
		assertNotNull(sid);

	}
}
