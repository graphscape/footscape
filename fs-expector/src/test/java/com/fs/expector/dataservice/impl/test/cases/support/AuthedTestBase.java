/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 29, 2012
 */
package com.fs.expector.dataservice.impl.test.cases.support;

import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.expector.dataservice.api.NodeTypes;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.api.wrapper.SignupConfirm;
import com.fs.expector.dataservice.api.wrapper.SignupRequest;

/**
 * @author wu
 * 
 */
public class AuthedTestBase extends TestBase {
	public static String confirmcode = "confirmcode1";

	protected Session createAccountAndLogin(String email, String password,
			String nick) {
		SignupRequest sr = this.signupRequest(email, password, nick);
		SignupConfirm sc = this.signupConfirm(sr);
		Account acc = this.account(sc);
		Session rt = this.login(acc);

		return rt;
	}

	protected SignupRequest signupRequest(String email, String password,
			String nick) {
		String signupId = null;

		SignupRequest nw = new SignupRequest().forCreate(this.datas);
		nw.setConfirmCode(confirmcode);
		nw.setPassword(password);
		nw.setEmail(email);
		nw.setNick(nick);
		nw.save(true);

		signupId = (String) nw.getId();

		assertNotNull("uid is null", signupId);

		//
		return nw;
	}

	protected SignupConfirm signupConfirm(SignupRequest sr) {

		String signupId = sr.getId();
		SignupRequest signup = this.datas.getNewestById(SignupRequest.class,
				signupId, false);

		assertNotNull("no signup with id:" + signupId, signup);
		assertEquals("signup got but id is null", signupId, signup.getId());
		assertEquals(sr.getEmail(), signup.getEmail());
		assertEquals(sr.getNick(), signup.getNick());
		assertEquals(sr.getPassword(), signup.getPassword());

		// confirm the request
		SignupConfirm nw = new SignupConfirm().forCreate(this.datas);

		nw.setSignupRequestId(signup.getId());
		nw.save(true);
		String id = (String) nw.getId();

		assertNotNull("uid is null", id);

		return nw;
	}

	protected Account account(SignupConfirm sc) {
		// the account,to be active

		SignupRequest signup = this.datas.getNewestById(SignupRequest.class,
				sc.getSignupRequestId(), true);//

		// create account

		Account acc = new Account().forCreate(this.datas);

		acc.setPassword(signup.getPassword());
		acc.setIsAnonymous(false);//
		acc.setNick(signup.getNick());
		acc.save(true);

		assertNotNull("id is null", acc.getId());
		assertEquals(signup.getPassword(), acc.getPassword());
		return acc;
	}

	protected Session login(Account acc) {

		// query account
		// this.dump();
		NodeQueryOperationI<Account> qo = this.datas
				.prepareOperation(NodeQueryOperationI.class);

		qo.nodeType(NodeTypes.ACCOUNT);
		qo.id(acc.getId());

		// qo.propertyEq(Account.EMAIL, acc.getEmail());
		qo.propertyEq(Account.PASSWORD, acc.getPassword());

		qo.execute();

		// qo.propertyEq("uniqueId", acc.getUniqueId());

		NodeQueryResultI<Account> rsts = qo.getResult();
		this.dump();//
		assertEquals("no account found with email and password:" + acc.getId()
				+ "," + acc.getPassword(), 1, rsts.size());
		Account a2 = rsts.single(true);
		// login

		NodeCreateOperationI co = this.datas.prepareOperation(
				NodeCreateOperationI.class).refreshAfterCreate(true);

		Session rt = new Session().forCreate(datas);
		rt.setProperty(Session.PK_IS_ANONYMOUS, false);
		rt.setClientId("todo");//
		rt.setAccountId(acc.getId());//
		rt.save(true);
		return rt;
	}
}
