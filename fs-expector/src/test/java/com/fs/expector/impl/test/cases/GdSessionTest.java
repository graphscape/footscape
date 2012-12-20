/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.test.cases;

import com.fs.expector.impl.test.cases.support.TestBase;
import com.fs.expector.impl.test.mock.MockClientFactory;
import com.fs.gridservice.commons.api.session.SessionManagerI;

/**
 * @author wuzhen
 * 
 */
public class GdSessionTest extends TestBase {

	public void testSession() throws Exception {
		MockClientFactory cf = new MockClientFactory().start();

		SessionManagerI sm = this.container.find(SessionManagerI.class, true);

	}

}
