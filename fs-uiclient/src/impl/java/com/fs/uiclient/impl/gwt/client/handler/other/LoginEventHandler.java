/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class LoginEventHandler extends UiHandlerSupport implements EventHandlerI<UserLoginEvent> {

	/**
	 * @param c
	 */
	public LoginEventHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(UserLoginEvent t) {
		HeaderModelI hm = this.getModel(HeaderModelI.class, true);
		HeaderModelI.ItemModel hi = hm.getItem("user", true);
		String dname = t.getUserInfo().getString("nick", true);
		hi.setDisplayName(dname);
	}

}
