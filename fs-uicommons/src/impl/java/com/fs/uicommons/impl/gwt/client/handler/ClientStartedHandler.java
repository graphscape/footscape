/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.HeaderItems;
import com.fs.uicommons.api.gwt.client.event.AutoLoginRequireEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicommons.impl.gwt.client.EndpointKeeper;
import com.fs.uicommons.impl.gwt.client.handler.message.LoginFailureMH;
import com.fs.uicommons.impl.gwt.client.handler.message.LoginSuccessMH;
import com.fs.uicommons.impl.gwt.client.handler.message.PasswordForgotFailureMH;
import com.fs.uicommons.impl.gwt.client.handler.message.PasswordForgotSuccessMH;
import com.fs.uicommons.impl.gwt.client.handler.message.PasswordResetFailureMH;
import com.fs.uicommons.impl.gwt.client.handler.message.PasswordResetSuccessMH;
import com.fs.uicommons.impl.gwt.client.handler.message.SignupAnonymousSuccessMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.window.UiWindow;

/**
 * @author wuzhen
 * 
 */
public class ClientStartedHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {

	/**
	 * @param c
	 */
	public ClientStartedHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(AfterClientStartEvent e) {
		
		this.activeMessageHandlers(this.container, e.getClient());
		
		//
		//heatbeat
		EndpointKeeper ek = new EndpointKeeper(this.getClient(true));
		ek.start();//
		
		// start frwk view.
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		fc.open();
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		//
		String action = UiWindow.getParameter("action", null);
		if (action == null) {
			// TODO remove this event
			new AutoLoginRequireEvent(e.getSource()).dispatch();
		} else {
			Map<String, List<String>> pm = com.google.gwt.user.client.Window.Location.getParameterMap();
			if (action.equals("pf")) {
				String pfId = UiWindow.getParameter("pfId", null);
				if (pfId == null) {
					throw new UiException("pfId is null");
				}
				LoginControlI lc = this.getControl(LoginControlI.class, true);
				PasswordResetViewI pv = lc.openPasswordResetView();
				pv.setPfId(pfId);

			} else {
				throw new UiException("no this action:" + action);
			}
		}
	}
	
	public void activeMessageHandlers(ContainerI c, UiClientI client) {
		EndPointI ep = client.getEndpoint(true);
		ep.addHandler(Path.valueOf("/endpoint/message/signup/anonymous/success"),
				new SignupAnonymousSuccessMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), new LoginSuccessMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/failure"), new LoginFailureMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/success"), new PasswordForgotSuccessMH(
				c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/failure"), new PasswordForgotFailureMH(
				c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/reset/failure"), new PasswordResetFailureMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/password/reset/success"), new PasswordResetSuccessMH(c));

	}

}
