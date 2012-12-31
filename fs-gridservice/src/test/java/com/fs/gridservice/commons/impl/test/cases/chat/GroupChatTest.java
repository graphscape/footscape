/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.test.cases.chat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fs.commons.api.message.MessageI;
import com.fs.gridservice.commons.api.mock.MockClient;
import com.fs.gridservice.commons.impl.mock.MockClientImpl;
import com.fs.gridservice.commons.impl.test.cases.support.TestBase;
import com.fs.gridservice.commons.impl.test.mock.chat.MockClientChatGroup;
import com.fs.gridservice.commons.impl.test.mock.chat.MockParticipant;

/**
 * @author wu
 * 
 */
public class GroupChatTest extends TestBase {

	public void testGroupChat() throws Exception {

		String accId1 = "acc1";
		String accId2 = "acc2";
		String accId3 = "acc3";

		MockClient c1 = this.newClientAndAuth(accId1);
		MockClient c2 = this.newClientAndAuth(accId2);
		MockClient c3 = this.newClientAndAuth(accId3);
		MockClientChatGroup gc1 = newChatRoom("group1", c1);

		MockClientChatGroup gc2 = newChatRoom("group1", c2);

		MockClientChatGroup gc3 = newChatRoom("group1", c3);
		MockParticipant pt = null;
		List<MockParticipant> pL = null;
		{ // acc1 join
			gc1.join().get(5000, TimeUnit.MILLISECONDS);
			assertTrue("acc1 should join", gc1.isJoined());
			// wait the join// of himself
			gc1.waitNewJoin(10, TimeUnit.SECONDS);

			// check ptlist

			assertTrue("c1 should in group", gc1.containsAccountId(accId1));
		}
		{// acc2 join
			gc2.join().get(5000, TimeUnit.MILLISECONDS);
			assertTrue("acc2 should join", gc2.isJoined());
			{// c2 join c1
				pt = gc1.waitNewJoin(10, TimeUnit.SECONDS);
				assertEquals(
						"c2 should joined c1,but was:" + gc1.getAccIdList(),
						accId2, pt.getAccountId());
			}
			{// c2's list
				pt = gc2.waitNewJoin(10, TimeUnit.SECONDS);
				assertEquals("c1 should join c2 first", accId1,
						pt.getAccountId());

				pt = gc2.waitNewJoin(10, TimeUnit.SECONDS);
				assertEquals("c2 should join c2 second", accId2,
						pt.getAccountId());

				assertTrue(
						"c1 should in group of c2,but was:"
								+ gc2.getAccIdList(),
						gc1.containsAccountId(accId1));
				assertTrue(
						"c2 should in group of c2,but was:"
								+ gc2.getAccIdList(),
						gc1.containsAccountId(accId2));
			}
		}

		{// acc3 join
			gc3.join().get(5000, TimeUnit.MILLISECONDS);
			assertTrue("acc3 should join", gc3.isJoined());

			// c1:
			pt = gc1.waitNewJoin(10, TimeUnit.SECONDS);
			assertTrue("c3 should join c1,all:" + gc1.getAccIdList(),
					gc1.containsAccountId(accId3));

			// c2:
			pt = gc2.waitNewJoin(10, TimeUnit.SECONDS);
			assertEquals("c3 should join c2" + gc2.getAccIdList(), accId3,
					pt.getAccountId());
			assertTrue("c3 should join c2 , all:" + gc2.getAccIdList(),
					gc2.containsAccountId(accId3));

			// c3:
			pt = gc3.waitNewJoin(10, TimeUnit.SECONDS);
			assertEquals("c1 should join first" + gc3.getAccIdList(), accId1,
					pt.getAccountId());

			pt = gc3.waitNewJoin(10, TimeUnit.SECONDS);
			assertEquals("c2 should join second", accId2, pt.getAccountId());

			pt = gc3.waitNewJoin(10, TimeUnit.SECONDS);
			assertEquals("c3 should join last", accId3, pt.getAccountId());

			assertTrue("c1 should in group", gc3.containsAccountId(accId1));
			assertTrue("c2 should in group", gc3.containsAccountId(accId2));
			assertTrue("c3 should in group,all:" + gc3.getAccIdList(),
					gc3.containsAccountId(accId3));

		}
		// send message from acc1
		gc1.sendMessage("from c1");
		{
			MessageI msg = gc2.receiveMessage(5000000, TimeUnit.MILLISECONDS);
			assertNotNull("message from c1 not received by c2", msg);
			String text = (String) msg.getPayload("text");
			assertEquals("from c1", text);
		}
		{
			MessageI msg = gc1.receiveMessage(5000000, TimeUnit.MILLISECONDS);
			assertNotNull("message from c1 not received by him self", msg);
			String text = (String) msg.getPayload("text");
			assertEquals("from c1", text);
		}
		{
			MessageI msg = gc3.receiveMessage(5000000, TimeUnit.MILLISECONDS);
			assertNotNull("message from c1 not received by c3", msg);
			String text = (String) msg.getPayload("text");
			assertEquals("from c1", text);
		}

		{// accId1 exit from group
			gc1.exit().get(500, TimeUnit.SECONDS);
			assertFalse("should exit ", gc1.isJoined());
			// check c2's list
			pt = gc2.waitNewExit(10, TimeUnit.SECONDS);
			assertEquals("c1 should exit", accId1, pt.getAccountId());
			// check c3's list
			pt = gc3.waitNewExit(10, TimeUnit.SECONDS);
			assertEquals("c1 should exit", accId1, pt.getAccountId());

		}
		{// accId2 exit from group
			gc2.exit().get(5, TimeUnit.SECONDS);
			assertFalse("should exit ", gc2.isJoined());
			// check c3's list
			pt = gc3.waitNewExit(10, TimeUnit.SECONDS);
			assertEquals("c2 should exit", accId2, pt.getAccountId());

		}
		{// last exist
			gc3.exit().get(5, TimeUnit.SECONDS);
			assertFalse("should exit ", gc3.isJoined());

		}
	}

	protected MockClientChatGroup newChatRoom(String gcid, MockClient mc)
			throws Exception {
		MockClientChatGroup rt = new MockClientChatGroup(gcid, mc);
		return rt;

	}

}
