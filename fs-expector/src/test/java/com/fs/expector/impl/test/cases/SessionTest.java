/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.test.cases;

import com.fs.expector.api.session.SessionI;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.test.cases.support.TestBase;

/**
 * @author wuzhen
 * 
 */
public class SessionTest extends TestBase {

	public void testSession() {
		SessionManagerI sm = this.container.find(SessionManagerI.class);
		SessionI s = sm.newMember();
		String sid = s.getId();

		String accid = "acc001";
		s.setProperty("accountId", accid);
		sm.addMember(s);

		s = sm.getMember(sid);
		assertNotNull("session is null", s);
		String accid2 = (String) s.getProperty("accountId");
		assertEquals(accid, accid2);
	}

}
