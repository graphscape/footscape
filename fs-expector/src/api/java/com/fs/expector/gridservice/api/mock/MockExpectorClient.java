/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.api.mock;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.mock.MockClientWrapper;

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

	/**
	 * @param c
	 */
	public MockExpectorClient(AClientI mc, PropertiesI pts) {
		super(mc, pts);

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

	public void signup(final String email, String nick, String pass) {

		MessageI req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//

		MessageI res = this.syncSendMessage(req);

	}

	protected MessageI newRequest(String path) {
		MessageI rt = new MessageSupport();
		rt.setHeader(MessageI.HK_PATH, path);
		String tid = this.getTerminalId();//
		rt.setHeader(MessageI.HK_RESPONSE_ADDRESS, "tid://" + tid);
		return rt;
	}

	/*
	 * Dec 30, 2012
	 */

	public String newExp(String body) {
		MessageI req = this.newRequest("/expe/submit");
		req.setPayload("body", body);
		req.setPayload("title", body);
		req.setPayload("format", "n/a");//
		req.setPayload("summary", "n/a");
		MessageI i = this.syncSendMessage(req);
		i.assertNoError();
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

	public List<MockExpItem> getConnectedExp(String expId) {
		//
		MessageI req = this.newRequest("/exps/connected");

		req.setPayload("expId1", expId);
		MessageI res = this.syncSendMessage(req);

		return this.processExpItemResult(res);
	}

	/*
	 * Dec 30, 2012
	 */

	public void cooperConfirm(String crid, boolean findAct) {
		//
		MessageI req = this.newRequest("/cooper/confirm");

		req.setPayload("cooperRequestId", crid);

		MessageI i = this.syncSendMessage(req);

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
		MessageI res = this.syncSendMessage(req);

		return this.processExpItemResult(res);

	}

	public List<MockExpItem> processExpItemResult(MessageI res) {
		List<PropertiesI<Object>> el = (List<PropertiesI<Object>>) res.getPayload("expectations");
		List<MockExpItem> rt = new ArrayList<MockExpItem>();
		for (PropertiesI<Object> pts : el) {
			MockExpItem me = new MockExpItem(pts);

			rt.add(me);
		}
		return rt;
	}

	/**
	 * Mar 5, 2013
	 */
	public List<MockExpMessage> getExpMessage(String expId2) {

		//
		List<MockExpMessage> rt = new ArrayList<MockExpMessage>();
		MessageI req = this.newRequest("/expm/search");
		req.setPayload("expId2", expId2);
		MessageI res = this.syncSendMessage(req);
		res.assertNoError();
		List<MessageI> el = (List<MessageI>) res.getPayload("expMessages", true);
		for (MessageI pts : el) {
			MockExpMessage me = new MockExpMessage(pts);

			rt.add(me);
		}
		return rt;
	}

}
