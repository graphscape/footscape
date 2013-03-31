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
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
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

}
