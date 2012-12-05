/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.xmpp.impl.test.cases;

import com.fs.xmpp.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class RegisterTest extends TestBase {

	public void testRegister() {
		
		this.xmpp.register("testuser1", "testpassword1");
		
	}
}
