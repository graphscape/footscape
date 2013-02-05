/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicore.api.gwt.client.mock;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class MockUiClient extends UiObjectSupport {

	protected UiClientI client;

	protected EndPointI endpoint;

	protected String email;

	public MockUiClient(UiClientI client) {
		super(client.getContainer());

		this.client = client;
		this.endpoint = this.client.getEndpoint();
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/signup/submit/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						MockUiClient.this.onSignupRequestSuccess(t);
					}
				});
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/signup/confirm/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						new MockSignupEvent(MockUiClient.this).dispatch();
					}
				});
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/terminal/unbinding/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						new MockSignupEvent(MockUiClient.this).dispatch();
					}
				});
		this.endpoint.addHandler(EndpointBondEvent.TYPE, new EventHandlerI<EndpointBondEvent>() {

			@Override
			public void handle(EndpointBondEvent t) {
				MockUiClient.this.onBond();
			}
		});
	}

	protected MsgWrapper newRequest(String path) {
		return new MsgWrapper(Path.valueOf(path));
	}

	public void signup(String email, String nick, String pass) {

		this.email = email;
		MsgWrapper req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "resp");//

		this.endpoint.sendMessage(req);
	}

	public void login(String email, String pass) {
		//
		this.email = email;
		PropertiesData<Object> cre = new PropertiesData<Object>();
		cre.setProperty("type", ("registered"));
		cre.setProperty("email", (email));
		cre.setProperty("password", (pass));
		endpoint.auth(cre);

	}

	public void logout() {
		this.endpoint.logout();
	}

	protected void onSignupRequestSuccess(MsgWrapper evt) {
		MessageData t = evt.getTarget();
		String ccode = t.getString("confirmCode", true);
		MsgWrapper req = this.newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);

		this.endpoint.sendMessage(req);
	}

	public void onBond() {

	}
}
