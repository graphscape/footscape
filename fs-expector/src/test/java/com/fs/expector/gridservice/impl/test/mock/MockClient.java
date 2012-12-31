/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.test.mock;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.MessageServiceI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.expector.gridservice.api.Constants;
import com.fs.expector.gridservice.api.TestHelperI;
import com.fs.expector.gridservice.api.mock.MockActivity;

/**
 * @author wu
 * 
 */
public class MockClient {

	private String sessionId;

	private String clientId;

	private String accountId;

	private MessageServiceI engine;

	private ContainerI container;

	private String nick;

	public List<MockActivity> activityIdList = new ArrayList<MockActivity>();

	protected DataServiceI dataService;

	public MockClient(ContainerI c) {
		this.container = c;
		this.engine = null;//

		this.dataService = c.find(DataServiceI.class, true);
	}

	public void start() {

		this.clientId = this.clientInit();//
		this.sessionId = this.login();
	}

	public String signupAndLogin(String email, String nick) {

		String ccode = this.signupRequest(email, nick);
		this.signupConfirm(email, ccode);

		String rt = this.login(null, email, nick);
		this.sessionId = rt;
		return rt;
	}

	protected String clientInit() {// get sessionUid

		MessageI req = new MessageSupport("/uiserver/client/init");
		ResponseI res = this.service(req);
		res.assertNoError();

		return (String) res.getPayload("clientId", true);

	}

	// return login uid;
	protected String login(String accId, String email, String password) {

		MessageI req = newRequest("/uiserver/login/submit");
		// req.setPayload("accountId","");//by accountId or email
		if (accId == null) {
			req.setPayload("isSaved", Boolean.FALSE);
			req.setPayload("type", "registered");
			req.setPayload("email", email);// email may be null
		} else {
			req.setPayload("isSaved", Boolean.TRUE);
			req.setPayload("type", "anonymous");//
			req.setPayload("accountId", accId);// accountId may be null
		}
		req.setPayload("password", password);//

		ResponseI res = this.service(req);

		res.assertNoError();
		String accId2 = (String) res.getPayload("accountId", true);
		this.accountId = accId2;//
		return (String) res.getPayload("sessionId", true);

	}

	protected String login() {

		MessageI req = newRequest("/uiserver/login/anonymous");
		ResponseI res = this.service(req);
		res.assertNoError();

		String accId = (String) res.getPayload("accountId");// created for
															// annomymous user.
		String password = (String) res.getPayload("password");
		return this.login(accId, null, password);
	}

	protected ResponseI service(MessageI req) {
		ResponseI rt = this.engine.service(req);
		if (rt.getErrorInfos().hasError()) {
			DumpOperationI op = this.dataService
					.prepareOperation(DumpOperationI.class);
			op.execute().getResult().assertNoError();
		}
		rt.assertNoError();

		return rt;
	}

	protected MessageI newRequest(String path) {
		MessageI rt = new MessageSupport(path);
		rt.setHeader(Constants.HK_CLIENT_ID, this.clientId);
		return rt;

	}

	protected String signupRequest(String email, String nick) {

		MessageI req = newRequest("/uiserver/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", nick);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "test");//

		ResponseI res = this.service(req);
		res.assertNoError();

		TestHelperI th = this.container.find(TestHelperI.class);
		String rt = th.getConfirmCode(email, true);

		return rt;
	}

	protected void signupConfirm(String email, String ccode) {

		MessageI req = newRequest("/uiserver/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);
		ResponseI res = this.service(req);
		res.assertNoError();
		// String rt = (String) res.getPayload("accountUid", true);

		// return rt;

	}

	public String newExp(String body) {
		MessageI req = this.newRequest("/uiserver/expe/submit");
		req.setPayload("body", body);
		ResponseI res = this.service(req);
		res.assertNoError();
		String rt = (String) res.getPayload("expId", true);

		return rt;
	}

	/**
	 * Nov 3, 2012
	 */
	public String cooperRequest(String expId1, String expId2) {
		MessageI req = this.newRequest("/uiserver/cooper/request");

		req.setPayload("expId1", expId1);

		req.setPayload("expId2", expId2);

		ResponseI res = this.service(req);
		res.assertNoError();
		String rt = (String) res.getPayload("cooperMessageId", true);

		return rt;
	}

	/**
	 * Nov 3, 2012
	 */
	public void cooperConfirm(String cooperId, boolean findAct) {
		MessageI req = this.newRequest("/uiserver/cooper/confirm");

		req.setPayload("cooperMessageId", cooperId);

		req.setPayload("useNewestActivityId", findAct);

		ResponseI res = this.service(req);
		res.assertNoError();

	}

	/**
	 * Nov 3, 2012
	 */
	public List<MockActivity> refreshActivity() {
		// ActivitiesHandler
		MessageI req = this.newRequest("/uiserver/activities/activities");
		ResponseI res = this.service(req);
		res.assertNoError();
		PropertiesI pts = res.getPayloads();
		List<PropertiesI> actList = (List<PropertiesI>) pts.getProperty(
				"activities", true);
		this.activityIdList.clear();//
		for (PropertiesI apt : actList) {
			MockActivity ma = new MockActivity();
			ma.actId = (String) apt.getProperty("actId", true);
			// ma.accId = (String) apt.getProperty("accountId", true);

			this.activityIdList.add(ma);
		}
		return this.activityIdList;
	}

	public MockUserSnapshot getUserSnapshot(boolean forceRefresh) {
		MessageI req = this.newRequest("/uiserver/usshot/snapshot");
		req.setPayload("refresh", forceRefresh);
		ResponseI res = this.service(req);

		res.assertNoError();

		PropertiesI<Object> pts = res.getPayloads();
		List<String> activityIds = (List<String>) pts.getProperty(
				"activityIdList", true);
		List<String> expIds = (List<String>) pts.getProperty("expIdList", true);
		List<String> corrIds = (List<String>) pts.getProperty(
				"cooperMessageIdList", true);
		MockUserSnapshot rt = new MockUserSnapshot(this.accountId);
		rt.addActivityIdList(activityIds);
		rt.addExpIdList(expIds);
		rt.addCooperMessageIdList(corrIds);
		return rt;
	}
}
