/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import com.fs.expector.gridservice.impl.test.cases.support.SignupTestBase;
import com.fs.webcomet.api.WebCometComponents;

/**
 * @author wuzhen
 * 
 */
public class SignupTest extends SignupTestBase {

	/**
	 * @param p
	 */
	public SignupTest() {
		super(WebCometComponents.AJAX);
	}

	public void testSignup() throws Exception {
		this.doTestSignup();

	}
}
