/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.mock;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.message.support.QueueMessageHandler;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.api.TestHelperI;
import com.fs.expector.gridservice.api.mock.MockExpItem;
import com.fs.expector.gridservice.api.mock.MockActivity;
import com.fs.expector.gridservice.api.mock.MockActivityDetail;
import com.fs.expector.gridservice.api.mock.MockExpInfo;
import com.fs.expector.gridservice.api.mock.MockExpectorClient;
import com.fs.gridservice.commons.api.mock.MockClient;

/**
 * @author wuzhen
 * 
 */
public class MockExpectorClientImpl extends MockExpectorClient {

	protected ContainerI container;
	protected DispatcherI<MessageContext> dispatcher;

	/**
	 * @param mc
	 */
	public MockExpectorClientImpl(MockClient mc, ContainerI container) {
		super(mc);
		this.container = container;
		this.dispatcher = this.getDispatcher();
	}

	public MessageI syncSendMessage(MessageI msg) {
		return this.syncSendMessage(msg, 5 * 1000);//
	}

	@Override
	public MessageI syncSendMessage(MessageI msg, int timeout) {
		Path mp = msg.getPath();

		QueueMessageHandler qh = new QueueMessageHandler();
		this.dispatcher.addHandler(mp, qh);
		this.sendMessage(msg);

		MessageContext mc = qh.poll(timeout);
		if (mc == null) {
			throw new FsException("timeout:" + timeout + " to wait the success response for request:" + msg);
		}
		MessageI rt = mc.getRequest();
		Path rp = rt.getPath();
		if (!"success".equals(rp.getName())) {
			throw new FsException("failure response:" + rt);
		}

		return rt;

	}

	@Override
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
		req.setPayload("confirmCodeNotifier", "test");//
		QueueMessageHandler qh = new QueueMessageHandler();

		this.dispatcher.addHandler(Path.valueOf("/signup/submit"), qh);

		this.sendMessage(req);

		qh.take().getResponse().assertNoError();
		TestHelperI th = MockExpectorClientImpl.this.container.find(TestHelperI.class);
		String rt = th.getConfirmCode(email, true);

		return rt;
	}

	protected void signupConfirm(String email, String ccode) {

		MessageI req = newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);
		QueueMessageHandler qh = new QueueMessageHandler();

		this.dispatcher.addHandler(Path.valueOf("/signup/confirm"), qh);

		this.sendMessage(req);
		qh.take().getResponse().assertNoError();

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
	@Override
	public void start(String email, String nick, String pass) {
		//
		this.connect();
		this.signup(email, nick, pass);
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty("type", "registered");
		cre.setProperty("email", email);
		cre.setProperty("password", pass);

		this.auth(cre);
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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

	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.expector.gridservice.api.mock.MockExpectorClient#close()
	 */
	@Override
	public void close() {

	}
}
