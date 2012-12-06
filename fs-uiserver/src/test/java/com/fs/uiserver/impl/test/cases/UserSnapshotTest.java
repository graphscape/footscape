/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.uiserver.impl.test.cases;

import java.util.List;

import com.fs.uiserver.impl.test.cases.support.AuthedTestBase;
import com.fs.uiserver.impl.test.mock.MockClient;
import com.fs.uiserver.impl.test.mock.MockClients;
import com.fs.uiserver.impl.test.mock.MockUserSnapshot;

/**
 * @author wu
 * 
 */
public class UserSnapshotTest extends AuthedTestBase {

	/**
	 * 1 ACC1: exp11,exp12,exp13;
	 * <p>
	 * 2 ACC2: exp21,exp22,exp23;
	 * <p>
	 * 3 ACC1: cr11-21,cr12-22;
	 * <p>
	 * 4 ACC2: cr21-11,cr22-12;
	 * <p>
	 * 5 ACC1: cc-cr11-21
	 * <p>
	 * 5.1 act1-for-exp11/exp21 5.2 acc1-act1, acc2-act1
	 * <p>
	 * 6 ACC2: cc-cr21-11
	 * <p>
	 * note: acc2 has:1)exp21,2) a cr-for-exp21,3) An activity already with
	 * exp21, How can acc2 to do for now?
	 * <p>
	 * What does mean by confirm the request?
	 * <p>
	 * or the request should be withdraw/ignored?
	 * <p>
	 * 
	 * Dec 6, 2012
	 */
	public void testUserSnapshot() {
		MockClients cs = new MockClients(this.container);
		int CLS = 2;// 2 clients.
		int EXPS = 3;
		cs.start(CLS);//
		cs.createExpections(3);//
		for (int i = 0; i < CLS; i++) {
			MockClient mc = cs.getClient(i);
			MockUserSnapshot us = mc.getUserSnapshot(true);
			List<String> expIdL = us.getExpIdList();
			assertEquals("exps in snapshot error", EXPS, expIdL.size());

			List<String> crIdL = us.getCooperRequestIdList();
			assertEquals("crs in snapshot error", 0, crIdL.size());

			List<String> actIdL = us.getActivityIdList();
			assertEquals("acts in snapshot error", 0, actIdL.size());

		}
		int CRS = 2;
		cs.cooperRequest(CRS, false);
		for (int i = 0; i < CLS; i++) {
			MockClient mc = cs.getClient(i);
			MockUserSnapshot us = mc.getUserSnapshot(true);

			List<String> expIdL = us.getExpIdList();
			assertEquals("exps in snapshot error", EXPS, expIdL.size());

			List<String> crIdL = us.getCooperRequestIdList();
			assertEquals("crs in snapshot error", CRS, crIdL.size());

			List<String> actIdL = us.getActivityIdList();
			assertEquals("acts in snapshot error", 0, actIdL.size());

		}
		int CCS = 1;
		cs.cooperConfirm(CCS, false, true);
		for (int i = 0; i < CLS; i++) {
			MockClient mc = cs.getClient(i);
			MockUserSnapshot us = mc.getUserSnapshot(true);
			List<String> expIdL = us.getExpIdList();
			assertEquals("exps in snapshot error", EXPS, expIdL.size());

			List<String> crIdL = us.getCooperRequestIdList();
			assertEquals("crs in snaxpshot error", CRS - CCS, crIdL.size());

			List<String> actIdL = us.getActivityIdList();
			assertEquals("acts in snapshot error", CCS, actIdL.size());

		}

	}
}
