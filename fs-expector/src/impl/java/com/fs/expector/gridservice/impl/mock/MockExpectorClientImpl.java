/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.gridservice.api.TestHelperI;
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

	/**
	 * @param mc
	 */
	public MockExpectorClientImpl(MockClient mc, ContainerI container) {
		super(mc);
		this.container = container;
	}

	@Override
	public String signup(final String email, String nick, String pass) {
		String ccode = this.signupRequest(email, nick, pass);
		String rt = this.signupConfirm(email, ccode);
		return rt;
	}

	protected String signupRequest(final String email, String nick, String pass) {

		MessageI req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "test");//
		this.sendMessage(req);
		String rt = this.receiveAndProcessMessageAndGetResult(new CallbackI<MessageI, String>() {

			@Override
			public String execute(MessageI i) {
				TestHelperI th = MockExpectorClientImpl.this.container.find(TestHelperI.class);
				String rt = th.getConfirmCode(email, true);

				return rt;
			}
		});

		return rt;
	}

	protected String signupConfirm(String email, String ccode) {

		MessageI req = newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);
		this.sendMessage(req);
		String rt = this.receiveAndProcessMessageAndGetResult(new CallbackI<MessageI, String>() {

			@Override
			public String execute(MessageI i) {
				// String rt = i.getString("accountId", true);

				return null;
			}
		});
		return rt;
	}

	public <T> T receiveAndProcessMessageAndGetResult(final CallbackI<MessageI, T> cb) {
		Future<T> rtF = this.receiveAndProcessMessage(cb);

		try {
			return rtF.get();
		} catch (Exception e) {
			throw new FsException(e);
		}
	}

	public <T> Future<T> receiveAndProcessMessage(final CallbackI<MessageI, T> cb) {
		FutureTask<T> rt = new FutureTask<T>(new Callable<T>() {

			@Override
			public T call() throws Exception {
				MessageI msg = MockExpectorClientImpl.this.receiveAndGetMessage();

				return cb.execute(msg);
			}
		});
		rt.run();
		return rt;
	}

	public MessageI receiveAndGetMessage() {
		MessageI msg = null;
		try {
			msg = this.receiveMessage().get();
		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			throw new FsException(e);
		}
		return msg;
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
		try {
			this.connect().get();
		} catch (InterruptedException e) {
			throw new FsException(e);
		} catch (ExecutionException e) {
			throw new FsException(e);
		}
		this.signup(email, nick, pass);
		PropertiesI<Object> cre = new MapProperties<Object>();
		cre.setProperty("type", "registered");
		cre.setProperty("email", email);
		cre.setProperty("password", pass);

		this.auth(cre);
	}

	public <T> T sendMessageAndGetResult(MessageI msg, CallbackI<MessageI, T> cb) {
		this.sendMessage(msg);
		return this.receiveAndProcessMessageAndGetResult(cb);
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public String newExp(String body) {
		MessageI req = this.newRequest("/expe/submit");
		req.setPayload("body", body);

		String rt = this.sendMessageAndGetResult(req, new CallbackI<MessageI, String>() {

			@Override
			public String execute(MessageI i) {
				//
				String rt = (String) i.getPayload("expId", true);
				return rt;
			}
		});

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

		String rt = this.sendMessageAndGetResult(req, new CallbackI<MessageI, String>() {

			@Override
			public String execute(MessageI i) {
				//
				String rt = (String) i.getPayload("cooperMessageId", true);
				return rt;
			}
		});

		return rt;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public void cooperConfirm(String crid, boolean findAct) {
		//
		MessageI req = this.newRequest("/cooper/confirm");

		req.setPayload("cooperMessageId", crid);

		req.setPayload("useNewestActivityId", findAct);

		String rt = this.sendMessageAndGetResult(req, new CallbackI<MessageI, String>() {

			@Override
			public String execute(MessageI i) {
				//

				return null;
			}
		});

	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public List<MockActivity> refreshActivity() {
		//
		// ActivitiesHandler
		MessageI req = this.newRequest("/activities/activities");

		List<MockActivity> rt = this.sendMessageAndGetResult(req,
				new CallbackI<MessageI, List<MockActivity>>() {

					@Override
					public List<MockActivity> execute(MessageI i) {
						String path = i.getPath();

						PropertiesI pts = i.getPayloads();
						List<PropertiesI> actList = (List<PropertiesI>) pts.getProperty("activities", true);
						List<MockActivity> rt = new ArrayList<MockActivity>();
						for (PropertiesI apt : actList) {
							MockActivity ma = new MockActivity();
							ma.actId = (String) apt.getProperty("id", true);
							List<MockExpInfo> expList = new ArrayList<MockExpInfo>();
							List<PropertiesI<Object>> eL = (List<PropertiesI<Object>>) apt.getProperty(
									"expectations", true);
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
				});

		return rt;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public MockActivityDetail getActivityDetail(String actId) {
		//

		MessageI req = this.newRequest("/activity/detail");
		req.setPayload("actId", actId);
		MockActivityDetail rt = this.sendMessageAndGetResult(req,
				new CallbackI<MessageI, MockActivityDetail>() {

					@Override
					public MockActivityDetail execute(MessageI i) {
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
				});

		return rt;
	}

}
