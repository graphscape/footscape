/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 13, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.other;

import com.fs.uiclient.api.gwt.client.support.HandlerSupport;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class LoginEventHandler extends HandlerSupport implements EventHandlerI<UserLoginEvent> {

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void handle(UserLoginEvent t) {
		HeaderModelI hm = this.getModel(t, HeaderModelI.class, true);
		HeaderModelI.ItemModel hi = hm.getItem("user", true);
		String dname = t.getUserInfo().getString("nick", true);
		hi.setDisplayName(dname);
	}

}
