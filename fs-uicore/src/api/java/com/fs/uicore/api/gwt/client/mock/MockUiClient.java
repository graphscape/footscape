/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicore.api.gwt.client.mock;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
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
		this.client = client;
		this.endpoint = this.client.getEndpoint();
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/signup/submit/success"),
				new MessageHandlerI() {

					@Override
					public void handle(EndpointMessageEvent t) {
						MockUiClient.this.onSignupRequestSuccess(t);
					}
				});
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/signup/confirm/success"),
				new MessageHandlerI() {

					@Override
					public void handle(EndpointMessageEvent t) {
						new MockSignupEvent(MockUiClient.this).dispatch();
					}
				});
		this.endpoint.getMessageDispatcher().addHandler(Path.valueOf("/terminal/unbinding/success"),
				new MessageHandlerI() {

					@Override
					public void handle(EndpointMessageEvent t) {
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
		PropertiesData<UiData> cre = new PropertiesData<UiData>();
		cre.setProperty("type", StringData.valueOf("registered"));
		cre.setProperty("email", StringData.valueOf(email));
		cre.setProperty("password", StringData.valueOf(pass));
		endpoint.auth(cre);

	}

	public void logout() {
		this.endpoint.logout();
	}

	protected void onSignupRequestSuccess(EndpointMessageEvent evt) {
		MessageData t = evt.getMessage();
		String ccode = t.getString("confirmCode", true);
		MsgWrapper req = this.newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);

		this.endpoint.sendMessage(req);
	}

	public void onBond() {

	}
}
