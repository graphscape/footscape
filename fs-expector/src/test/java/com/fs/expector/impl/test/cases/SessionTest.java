/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.test.cases;

import java.util.UUID;

import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SessionTest extends TestBase {

	public void testSession() {
		SessionManagerI sm = this.container.find(SessionManagerI.class);

	}

}
