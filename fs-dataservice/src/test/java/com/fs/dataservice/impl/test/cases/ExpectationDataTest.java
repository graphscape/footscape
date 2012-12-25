/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.dataservice.impl.test.cases;

import java.util.List;

import org.elasticsearch.common.UUID;

import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.operations.CooperConfirmOperationI;
import com.fs.dataservice.api.expapp.result.CooperConfirmResultI;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.CooperConfirm;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.dataservice.impl.test.cases.support.AuthedTestBase;

/**
 * @author wu
 * 
 */
public class ExpectationDataTest extends AuthedTestBase {

	public void xtestUUID() {
		// System.out.println(UUIDUtil.randomHexUUID());
		System.out.println(UUID.randomBase64UUID());

	}

	public void testSignupNode() {

		String email1 = "user1@domain1.com";
		String password1 = "password1";
		String nick1 = "nick1";

		String email2 = "user2@domain2.com";
		String password2 = "password2";
		String nick2 = "nick2";

		String email3 = "user3@domain3.com";
		String password3 = "password3";
		String nick3 = "nick3";

		Session login1 = super.createAccountAndLogin(email1, password1, nick1);

		Expectation e11 = this.addExp(login1,
				"user1 has expectation1 key1 key2");
		Expectation e12 = this.addExp(login1,
				"user1 has expectation2 key3 key4");

		// this.dump();
		Session login2 = super.createAccountAndLogin(email2, password2, nick2);
		Expectation e21 = this.addExp(login2,
				"user2 has expectation1 key1 key2");
		Expectation e22 = this.addExp(login2,
				"user2 has expectation2 key3 key4");

		// this.dump();
		Session login3 = super.createAccountAndLogin(email3, password3, nick3);
		Expectation e31 = this.addExp(login2,
				"user3 has expectation1 key1 key2");
		Expectation e32 = this.addExp(login2,
				"user3 has expectation2 key3 key4");

		// this.dump();
		// login2 has exp 21 and request coper with 11 .

		CooperRequest cr21_11 = coperRequest(login2, e21, e11);
		// login1 confirm the request from login2.
		CooperConfirm cc21_11 = cooperConfirm(login1, cr21_11.getId(), null);// no
																				// activity
																				// with
																				// e11,to
																				// create
																				// a
																				// new
																				// Activity
		// there should be a activity created for the two exps.
		Activity act11 = getActivityByExp(e11);//
		Activity act21 = getActivityByExp(e21);

		assertEquals("activity for the 2 exp should be same", act11.getId(),
				act21.getId());

		List<Expectation> el1 = act11.getExpectationList();

		assertEquals("exp for the activity should be 2", 2, el1.size());// 11,21,31

		// more one exp 31 request 11 cooper
		CooperRequest cr31_11 = coperRequest(login3, e31, e11);
		CooperConfirm cc31_21 = cooperConfirm(login1, cr31_11.getId(),
				act11.getId());

		Activity act31 = getActivityByExp(e31);
		assertEquals("activity for th 3 exp should be same", act11.getId(),
				act31.getId());
		// TODO activity update or just let it there,leave to application to
		// collect all the data and explain it.
		// ChatRoom add

		List<Expectation> el = act11.getExpectationList();

		assertEquals("exp for the activity should be 3", 3, el.size());// 11,21,31

	}

	protected Activity getActivityByExp(Expectation ex) {
		this.dump();//
		NodeQueryOperationI<ExpActivity> no = this.datas.prepareNodeQuery(ExpActivity.TYPE);
		no.propertyEq(ExpActivity.PK_EXP_ID, ex.getId());
		NodeQueryResultI<ExpActivity> rst = no.execute().getResult().assertNoError().cast();
		List<ExpActivity> eaL = rst.list();
		assertEquals("no activity related with exp:" + ex.getId(), 1,
				eaL.size());
		String actId = eaL.get(0).getActivityId();
		Activity rt = this.datas.getNewestById(Activity.class, actId, true);
		return rt;
	}

	protected CooperConfirm cooperConfirm(Session login, String requestUid,
			String actId) {
		CooperConfirmOperationI cc = this.datas
				.prepareOperation(CooperConfirmOperationI.class);
		cc.cooperRequestId(requestUid);

		cc.exp2ActivityId(actId);
		cc.createNewActivity(actId == null);//
		cc.sessionId(login.getId());
		CooperConfirmResultI cr = cc.execute().getResult().assertNoError()
				.cast();

		CooperConfirm rt = this.datas.getNewestById(CooperConfirm.class,
				cr.getCooperConfirmId(), true);

		return rt;

	}

	protected CooperRequest coperRequest(Session login, Expectation exp1,
			Expectation exp2) {

		CooperRequest rt = new CooperRequest().forCreate(this.datas);
		rt.setAccountId1(login.getAccountId());
		rt.setExpId1(exp1.getId());
		rt.setExpId2(exp2.getId());
		rt.save(true);

		return rt;
	}

	protected Expectation addExp(Session login, String expBody) {

		Expectation rt = new Expectation().forCreate(this.datas);
		rt.setAccountId(login.getAccountId());
		rt.setBody(expBody);
		rt.save(true);// save

		return rt;
	}

	//
	public void schedule(String task) {

	}

}
