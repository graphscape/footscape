/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.event.RegisterUserLoginEvent;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;

/**
 * @author wuzhen
 * 
 */
public class EndpointBondHandler extends UiHandlerSupport implements EventHandlerI<EndpointBondEvent> {

	/**
	 * @param c
	 */
	public EndpointBondHandler(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(EndpointBondEvent e) {
		//
		UserInfo ui = e.getChannel().getUserInfo();
		if (ui.isAnonymous()) {
			new UserLoginEvent(e.getSource(), ui).dispatch();
		} else {
			new RegisterUserLoginEvent(e.getSource(), ui).dispatch();
		}
	}
}
