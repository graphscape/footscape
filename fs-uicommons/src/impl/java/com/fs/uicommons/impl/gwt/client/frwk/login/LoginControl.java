package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.event.RegisterUserLoginEvent;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * auth action is not login action,it will do auth like the submit. login just
 * open the login form.
 * 
 * @author wu
 * 
 */
public class LoginControl extends ControlSupport implements LoginControlI {

	public LoginControl(String name) {
		super(name);

	}

	/*
	 * Jan 12, 2013
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();
		this.getEventBus(true).addHandler(EndpointBondEvent.TYPE, new EventHandlerI<EndpointBondEvent>() {

			@Override
			public void handle(EndpointBondEvent e) {
				LoginControl.this.onEndpointBondEvent(e);
			}

		});
	}

	/**
	 * Jan 12, 2013
	 */
	protected void onEndpointBondEvent(EndpointBondEvent e) {
		//
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {
			new UserLoginEvent(this, ui).dispatch();
		} else {
			new RegisterUserLoginEvent(this, ui).dispatch();
		}
	}
}
