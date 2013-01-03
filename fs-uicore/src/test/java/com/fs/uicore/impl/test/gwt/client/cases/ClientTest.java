/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import org.junit.Test;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointUnbondEvent;
import com.fs.uicore.api.gwt.client.mock.MockSignupEvent;
import com.fs.uicore.api.gwt.client.mock.MockUiClient;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ClientTest extends TestBase {

	private String nick = "user1";
	private String email = nick + "@domain.com";
	private String pass = nick;
	private MockUiClient mc;

	@Test
	public void testClient() {
		this.finishing.add("start");
		this.finishing.add("signup");
		this.finishing.add("bond");
		this.finishing.add("unbond");

		mc = new MockUiClient(this.client);
		this.delayTestFinish(300 * 1000);

	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	protected void onEvent(Event e) {
		//
		super.onEvent(e);
		if (e instanceof EndpointBondEvent) {
			this.onBond();
		} else if (e instanceof EndpointUnbondEvent) {
			this.onUnbond();
		}
	}

	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		this.tryFinish("start");
		this.mc.addHandler(MockSignupEvent.TYPE, new EventHandlerI<MockSignupEvent>() {

			@Override
			public void handle(MockSignupEvent t) {
				ClientTest.this.tryFinish("signup");
				t.getMockUiClient().login(ClientTest.this.email, ClientTest.this.pass);
			}

		});
		this.mc.signup(email, nick, pass);

	}

	/**
	 * Jan 1, 2013
	 */

	public void onBond() {
		UserInfo ui = this.endpoint.getUserInfo();
		assertNotNull("user info is null", ui);
		ui.getAccountId();
		ui.getSessionId();
		ui.isAnonymous();
		this.tryFinish("bond");
		this.mc.logout();
	}

	public void onUnbond() {
		UserInfo ui = this.endpoint.getUserInfo();
		assertNull("user info is not null", ui);
		this.tryFinish("unbond");
	}

}
