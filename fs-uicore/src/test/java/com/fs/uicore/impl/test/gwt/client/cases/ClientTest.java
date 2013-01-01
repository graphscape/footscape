/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ClientTest extends TestBase {

	private String nick = "user1";
	private String email = nick + "@domain.com";
	private String pass = nick;

	@Test
	public void testClient() {
		this.finishing.add("signup-request");
		this.finishing.add("signup-confirm");
		this.finishing.add("bond");
		this.delayTestFinish(300 * 1000);

	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		MsgWrapper req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "resp");//
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/signup/submit/success"),
				new MessageHandlerI() {

					@Override
					public void handle(MessageData t) {
						ClientTest.this.onSignupRequestSuccess(t);
					}
				});
		this.endpoint.sendMessage(req);

	}

	/**
	 * Jan 1, 2013
	 */
	protected void onSignupRequestSuccess(MessageData t) {
		//
		this.tryFinish("signup-request");
		String ccode = t.getString("confirmCode", true);
		MsgWrapper req = this.newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/signup/confirm/success"),
				new MessageHandlerI() {

					@Override
					public void handle(MessageData t) {
						ClientTest.this.onSignupConfirm(t);
					}
				});
		this.endpoint.sendMessage(req);

	}

	protected MsgWrapper newRequest(String path) {
		return new MsgWrapper(Path.valueOf(path));
	}

	protected void onSignupConfirm(MessageData t) {// auth

		this.tryFinish("signup-confirm");

		this.endpoint.addHandler(EndpointBondEvent.TYPE, new EventHandlerI<EndpointBondEvent>() {

			@Override
			public void handle(EndpointBondEvent t) {
				ClientTest.this.onBond();
			}
		});
		PropertiesData<UiData> cre = new PropertiesData<UiData>();
		cre.setProperty("type", StringData.valueOf("registered"));
		cre.setProperty("email", StringData.valueOf(email));
		cre.setProperty("password", StringData.valueOf(pass));
		endpoint.auth(cre);

	}

	public void onBond() {
		this.tryFinish("bond");
	}

}
