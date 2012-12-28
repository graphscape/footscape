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
		MockExpectorClient client = this.cfactory.newClient();
		client.connect().get();
		client.ready(100000).get();
		String nick = "user1";
		String email = nick + "@domain.com";
		client.signup(email, nick);

	}
}
