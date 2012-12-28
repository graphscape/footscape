/**
 *  Dec 28, 2012
 */
package com.fs.expector.gridservice.impl.mock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.expector.gridservice.api.TestHelperI;
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
	public String signup(final String email, String nick) {
		String ccode = this.signupRequest(email, nick);
		String rt = this.signupConfirm(email, ccode);
		return rt;
	}

	protected String signupRequest(final String email, String nick) {

		MessageI req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", nick);//
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
				//String rt = i.getString("accountId", true);

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

}
