/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.uiserver.impl.test.cases.support;

import com.fs.uiserver.impl.test.mock.MockClient;

/**
 * @author wu
 * 
 */
public class AuthedTestBase extends TestBase {

	protected MockClient newClient() {
		return new MockClient(this.container);
	}

	protected MockClient startClient(){//anonymous
		MockClient rt = this.newClient();
		rt.start();
		return rt;
	}
	protected MockClient startClient(String email, String nick) {
		MockClient rt = this.startClient();
		rt.signupAndLogin(email, nick);
		
		return rt;
	}
}
