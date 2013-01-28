/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.api.mock;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.QueueMessageHandler;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;
import com.fs.websocket.api.mock.WSClient;

/**
 * @author wuzhen
 * 
 */
public class MockExpectorClient extends MockClientWrapper {

	public static final String SIGNUP_AT_CONNECT = "signupAtConnect";

	public static final String AUTH_WITH_SIGNUP = "authWithSignup";

	public static final String SIGNUP_EMAIL = "email";

	public static final String SIGNUP_NICK = "nick";

	public static final String SIGNUP_PASS = "pass";

	protected QueueMessageHandler queueMessageHandler;

	/**
	 * @param c
	 */
	public MockExpectorClient(WSClient mc) {
		super(mc);
		queueMessageHandler = new QueueMessageHandler();
		this.addHandler(Path.ROOT, queueMessageHandler);

	}

	public MessageI syncSendMessage(MessageI msg) {
		return this.syncSendMessage(msg, 5 * 1000);//
	}

	public MessageI syncSendMessage(MessageI msg, int timeout) {

		this.drainQueue();

		this.sendMessage(msg);

		return this.acquireNextMessage(timeout);

	}

	public List<MessageContext> drainQueue() {
		List<MessageContext> rt = new ArrayList<MessageContext>();
		this.queueMessageHandler.getQueue().drainTo(rt);
		return rt;

	}

	public MessageI acquireNextMessage(int timeout) {

		MessageContext mc = this.queueMessageHandler.poll(timeout);

		if (mc == null) {
			throw new FsException("timeout:" + timeout + " to wait the next message");
		}

		MessageI rt = mc.getRequest();
		Path rp = rt.getPath();
		if ("failure".equals(rp.getName())) {
			throw new FsException("failure response:" + rt);
		}

		return rt;
	}

	public void signup(final String email, String nick, String pass) {
		String ccode = this.signupRequest(email, nick, pass);
		this.signupConfirm(email, ccode);
	}

	protected String signupRequest(final String email, String nick, String pass) {

		MessageI req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "resp");//

		MessageI res = this.syncSendMessage(req);

		String rt = res.getString("confirmCode", true);

		return rt;
	}

	protected void signupConfirm(String email, String ccode) {

		MessageI req = newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);

		this.syncSendMessage(req);

	}

	protected MessageI newRequest(String path) {
		MessageI rt = new MessageSupport();
		rt.setHeader(MessageI.HK_PATH, path);
		String tid = this.getTerminalId();//
		rt.setHeader(MessageI.HK_RESPONSE_ADDRESS, "tid://" + tid);
		return rt;
	}

	/*
	 * Jan 28, 2013
	 */
	@Override
	public MockClientWrapper connect() {

		super.connect();
		boolean signup = this.properties.getPropertyAsBoolean(SIGNUP_AT_CONNECT, false);
		if (signup) {
			String email = (String) this.properties.getProperty(MockExpectorClient.SIGNUP_EMAIL, true);
			String nick = (String) this.properties.getProperty(MockExpectorClient.SIGNUP_NICK, true);
			String pass = (String) this.properties.getProperty(MockExpectorClient.SIGNUP_PASS, true);

			this.signup(email, nick, pass);
			boolean auth = this.properties.getPropertyAsBoolean(AUTH_WITH_SIGNUP, true);
			if (auth) {
				PropertiesI<Object> cre = new MapProperties<Object>();
				cre.setProperty("type", "registered");
				cre.setProperty("email", email);
				cre.setProperty("password", pass);

				this.auth(cre);
			}
		}
		return this;

	}

	/*
	 * Dec 30, 2012
	 */

	public String newExp(String body) {
		MessageI req = this.newRequest("/expe/submit");
		req.setPayload("body", body);

		MessageI i = this.syncSendMessage(req);
		String rt = (String) i.getPayload("expId", true);

		return rt;
	}

	/*
	 * Dec 30, 2012
	 */

	public String cooperRequest(String expId1, String expId2) {
		//
		MessageI req = this.newRequest("/cooper/request");
		req.setPayload("expId1", expId1);

		req.setPayload("expId2", expId2);
		MessageI i = this.syncSendMessage(req);
		//
		String rt = (String) i.getPayload("cooperRequestId", true);

		return rt;
	}

	/*
	 * Dec 30, 2012
	 */

	public void cooperConfirm(String crid, boolean findAct) {
		//
		MessageI req = this.newRequest("/cooper/confirm");

		req.setPayload("cooperRequestId", crid);

		req.setPayload("useNewestActivityId", findAct);
		MessageI i = this.syncSendMessage(req);

	}

	/*
	 * Dec 30, 2012
	 */

	public List<MockActivity> refreshActivity() {
		//
		// ActivitiesHandler
		MessageI req = this.newRequest("/activities/activities");
		MessageI i = this.syncSendMessage(req);
		Path path = i.getPath();

		PropertiesI pts = i.getPayloads();
		List<PropertiesI> actList = (List<PropertiesI>) pts.getProperty("activities", true);
		List<MockActivity> rt = new ArrayList<MockActivity>();
		for (PropertiesI apt : actList) {
			MockActivity ma = new MockActivity();
			ma.actId = (String) apt.getProperty("id", true);
			List<MockExpInfo> expList = new ArrayList<MockExpInfo>();
			List<PropertiesI<Object>> eL = (List<PropertiesI<Object>>) apt.getProperty("expectations", true);
			for (PropertiesI<Object> pt : eL) {
				MockExpInfo me = new MockExpInfo();
				me.accId = (String) pt.getProperty("accountId", true);
				me.expId = (String) pt.getProperty("expId", true);
				me.body = (String) pt.getProperty("body", true);

				expList.add(me);
			}
			ma.expList = expList;
			rt.add(ma);
		}
		return rt;
	}

	/*
	 * Dec 30, 2012
	 */

	public MockActivityDetail getActivityDetail(String actId) {
		//

		MessageI req = this.newRequest("/activity/refresh");
		req.setPayload("actId", actId);

		MessageI i = this.syncSendMessage(req);

		PropertiesI pts = i.getPayloads();
		List<PropertiesI> expList = (List<PropertiesI>) pts.getProperty("participants");
		MockActivityDetail rt = new MockActivityDetail();
		rt.activityUid = (String) pts.getProperty("actId", true);
		for (PropertiesI pt : expList) {
			String expId = (String) pt.getProperty("expId");
			String accId = (String) pt.getProperty("accountId");
			String body = (String) pt.getProperty("body");//

			MockExpInfo exp = new MockExpInfo();
			exp.accId = accId;
			exp.body = body;
			exp.expId = expId;

			rt.expMap.put(expId, exp);

		}
		return rt;
	}

	public List<MockExpItem> search(boolean includeMine, int firstResult, int maxResult, String expId,
			String phrase, int slop) {

		MessageI req = this.newRequest("/exps/search");
		req.setPayload("includeMine", includeMine);
		req.setPayload("firstResult", firstResult);
		req.setPayload("maxResult", maxResult);
		req.setPayload("expId", expId);
		req.setPayload("phrase", phrase);
		req.setPayload("slop", slop);
		MessageI i = this.syncSendMessage(req);

		List<PropertiesI<Object>> el = (List<PropertiesI<Object>>) i.getPayload("expectations");
		List<MockExpItem> rt = new ArrayList<MockExpItem>();
		for (PropertiesI<Object> pts : el) {
			MockExpItem me = new MockExpItem(pts);

			rt.add(me);
		}
		return rt;
	}

}
