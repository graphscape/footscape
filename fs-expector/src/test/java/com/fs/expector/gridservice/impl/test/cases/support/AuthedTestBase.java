/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.expector.gridservice.impl.test.cases.support;

import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.api.mock.MockExpectorClientFactory;

/**
 * @author wu
 * 
 */
public class AuthedTestBase extends TestBase {

	protected MockExpectorClient newClient() {
		return MockExpectorClientFactory.getInstance(this.container).newClient();
	}

	protected MockExpectorClient startClient(String email, String nick) {// anonymous
		MockExpectorClient rt = this.newClient();
		rt.start(email, nick, nick);
		return rt;
	}
}
