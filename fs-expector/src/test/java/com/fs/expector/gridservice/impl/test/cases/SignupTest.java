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
		String accId = client.signup("user1@domain.com", "user1");

	}
}
