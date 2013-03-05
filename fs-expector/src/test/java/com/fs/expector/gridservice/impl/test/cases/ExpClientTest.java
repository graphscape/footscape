/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import java.util.List;

import com.fs.expector.gridservice.api.mock.MockExpItem;
import com.fs.expector.gridservice.api.mock.MockExpMessage;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.AuthedTestBase;

/**
 * @author wu
 * 
 */
public class ExpClientTest extends AuthedTestBase {

	private MockExpectorClient client1;

	private MockExpectorClient client2;

	public void testExpAndConnect() {
		this.client1 = this.newClient("user1@domain1.com", "user1");
		this.client2 = this.newClient("user2@domain2.com", "user2");

		String body1 = "I expecting 1 ...";
		String body2 = "I expecting 2 ...";

		String expId1 = this.client1.newExp(body1);
		String expId2 = this.client2.newExp(body2);

		String cooperUid = this.client1.cooperRequest(expId1, expId2);

		this.client2.cooperConfirm(cooperUid, true);
		// the connection should be created.
		List<MockExpItem> eiL = this.client1.getConnectedExp(expId1);
		assertEquals(1, eiL.size());
		// there should be a message for exp2
		List<MockExpMessage> msgL = this.client2.getExpMessage(expId2);
		assertEquals(1, msgL.size());
		MockExpMessage msg = msgL.get(0);
		assertEquals(this.client1.getAccountId(), msg.getAccountId1());

	}

}
