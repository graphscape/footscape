/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.test.cases;

import java.util.List;

import com.fs.expector.gridservice.api.mock.MockActivity;
import com.fs.expector.gridservice.api.mock.MockActivityDetail;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.expector.gridservice.impl.test.cases.support.AuthedTestBase;

/**
 * @author wu
 * 
 */
public class ExpClientTest extends AuthedTestBase {

	private MockExpectorClient client1;

	private MockExpectorClient client2;

	public void testExpAndCooperAndActivity() {
		this.client1 = this.newClient("user1@domain1.com", "user1");
		this.client2 = this.newClient("user2@domain2.com", "user2");

		String body1 = "I expecting 1 ...";
		String body2 = "I expecting 2 ...";

		String expId1 = this.client1.newExp(body1);
		String expId2 = this.client2.newExp(body2);

		String cooperUid = this.client1.cooperRequest(expId1, expId2);

		this.client2.cooperConfirm(cooperUid, true);

		List<MockActivity> maL = this.client1.refreshActivity();
		assertEquals("should be one activity created for client1", 1, maL.size());

		List<MockActivity> maL2 = this.client2.refreshActivity();

		assertEquals("should be one activity created for client2", 1, maL2.size());
		MockActivity a1 = maL.get(0);
		MockActivity a2 = maL2.get(0);

		assertEquals("the two activity id should be same", a1.actId, a2.actId);//

		//
		MockActivityDetail md1 = this.client1.getActivityDetail(a1.actId);//
		MockActivityDetail md2 = this.client2.getActivityDetail(a2.actId);//
		assertEquals(a1.actId, md1.activityUid);

		assertEquals(2, md1.expMap.keySet().size());
		assertTrue(md1.expMap.keySet().contains(expId1));
		assertTrue(md1.expMap.keySet().contains(expId2));

	}

}
