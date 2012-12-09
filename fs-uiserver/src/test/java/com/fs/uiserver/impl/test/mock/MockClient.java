/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.test.mock;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.ServiceEngineI;
import com.fs.engine.api.support.RRContext;
import com.fs.uiserver.Constants;
import com.fs.uiserver.TestHelperI;

/**
 * @author wu
 * 
 */
public class MockClient {

	private String loginId;

	private String sessionId;

	private String accountId;

	private ServiceEngineI engine;

	private ContainerI container;

	private String nick;

	public List<MockUserActivity> activityIdList = new ArrayList<MockUserActivity>();

	protected DataServiceI dataService;

	public MockClient(ContainerI c) {
		this.container = c;
		this.engine = c.find(ServiceEngineI.class, true);
		this.dataService = c.find(DataServiceI.class, true);
	}

	public void start() {

		this.sessionId = this.clientInit();//
		this.loginId = this.login();
	}

	public String signupAndLogin(String email, String nick) {

		String ccode = this.signupRequest(email, nick);
		this.signupConfirm(email, ccode);

		String rt = this.login(null, email, nick);
		this.loginId = rt;
		return rt;
	}

	protected String clientInit() {// get sessionUid

		RequestI req = RRContext.newRequest();
		req.setPath("/uiserver/client/init");
		ResponseI res = this.service(req);
		res.assertNoError();

		return (String) res.getPayload("sessionId", true);

	}

	// return login uid;
	protected String login(String accId, String email, String password) {

		RequestI req = newRequest("/uiserver/login/submit");
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
		return (String) res.getPayload("loginId", true);

	}

	protected String login() {

		RequestI req = newRequest("/uiserver/login/anonymous");
		ResponseI res = this.service(req);
		res.assertNoError();

		String accId = (String) res.getPayload("accountId");// created for
															// annomymous user.
		String password = (String) res.getPayload("password");
		return this.login(accId, null, password);
	}

	protected ResponseI service(RequestI req) {
		ResponseI rt = this.engine.service(req);
		if (rt.getErrorInfos().hasError()) {
			DumpOperationI op = this.dataService
					.prepareOperation(DumpOperationI.class);
			op.execute().getResult().assertNoError();
		}
		rt.assertNoError();

		return rt;
	}

	protected RequestI newRequest(String path) {
		RequestI rt = RRContext.newRequest();
		rt.setHeader(Constants.HK_SESSION_ID, this.sessionId);
		rt.setPath(path);
		return rt;

	}

	protected String signupRequest(String email, String nick) {

		RequestI req = newRequest("/uiserver/signup/submit");
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

		RequestI req = newRequest("/uiserver/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);
		ResponseI res = this.service(req);
		res.assertNoError();
		// String rt = (String) res.getPayload("accountUid", true);

		// return rt;

	}

	public String newExp(String body) {
		RequestI req = this.newRequest("/uiserver/expe/submit");
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
		RequestI req = this.newRequest("/uiserver/cooper/request");

		req.setPayload("expId1", expId1);

		req.setPayload("expId2", expId2);

		ResponseI res = this.service(req);
		res.assertNoError();
		String rt = (String) res.getPayload("cooperRequestId", true);

		return rt;
	}

	/**
	 * Nov 3, 2012
	 */
	public void cooperConfirm(String cooperId, boolean findAct) {
		RequestI req = this.newRequest("/uiserver/cooper/confirm");

		req.setPayload("cooperRequestId", cooperId);

		req.setPayload("useNewestActivityId", findAct);

		ResponseI res = this.service(req);
		res.assertNoError();

	}

	/**
	 * Nov 3, 2012
	 */
	public List<MockUserActivity> refreshActivity() {
		RequestI req = this.newRequest("/uiserver/activities/refresh");
		ResponseI res = this.service(req);
		res.assertNoError();
		PropertiesI pts = res.getPayloads();
		List<PropertiesI> actList = (List<PropertiesI>) pts.getProperty(
				"activities", true);
		this.activityIdList.clear();//
		for (PropertiesI apt : actList) {
			MockUserActivity ma = new MockUserActivity();
			ma.actId = (String) apt.getProperty("actId", true);
			ma.accId = (String) apt.getProperty("accountId", true);

			this.activityIdList.add(ma);
		}
		return this.activityIdList;
	}

	public MockActivityDetail getActivityDetail(String actUid) {
		RequestI req = this.newRequest("/uiserver/activity/detail");
		req.setPayload("actId", actUid);
		ResponseI res = this.service(req);
		res.assertNoError();
		PropertiesI pts = res.getPayloads();
		List<PropertiesI> expList = (List<PropertiesI>) pts
				.getProperty("participants");
		MockActivityDetail rt = new MockActivityDetail();
		rt.activityUid = (String) pts.getProperty("actId", true);
		for (PropertiesI pt : expList) {
			String expId = (String) pt.getProperty("expId");
			String accId = (String) pt.getProperty("accountId");
			String body = (String) pt.getProperty("body");//

			MockExpectation exp = new MockExpectation();
			exp.accountId = accId;
			exp.body = body;
			exp.expUid = expId;

			rt.expMap.put(expId, exp);

		}

		return rt;

	}

	public MockUserSnapshot getUserSnapshot(boolean forceRefresh) {
		RequestI req = this.newRequest("/uiserver/usshot/snapshot");
		req.setPayload("refresh", forceRefresh);
		ResponseI res = this.service(req);

		res.assertNoError();

		PropertiesI<Object> pts = res.getPayloads();
		List<String> activityIds = (List<String>) pts.getProperty(
				"activityIdList", true);
		List<String> expIds = (List<String>) pts.getProperty("expIdList", true);
		List<String> corrIds = (List<String>) pts.getProperty(
				"cooperRequestIdList", true);
		MockUserSnapshot rt = new MockUserSnapshot(this.accountId);
		rt.addActivityIdList(activityIds);
		rt.addExpIdList(expIds);
		rt.addCooperRequestIdList(corrIds);
		return rt;
	}
}
