/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.impl.gwt.client.handler;

import com.fs.uicommons.api.gwt.client.HeaderItems;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.UiHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wuzhen
 * 
 */
public class UserLoginHandler extends UiHandlerSupport implements EventHandlerI<UserLoginEvent> {

	/**
	 * @param c
	 */
	public UserLoginHandler(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(UserLoginEvent e) {
		UserInfo ui = e.getUserInfo();
		//
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		HeaderViewI hv = fc.getHeaderView();
		String nick = (String) ui.getProperty("nick");
		if (nick == null) {
			nick = ui.getAccountId();//
		}
		// show a shorter name
		if (nick.length() > 9) {
			nick = nick.substring(0, 6) + "...";
		}
		fc.getHeaderView().setItemDisplayText(HeaderItems.H1_USER, false, nick);

		// both anonymous and register abable to logout

		hv.addItemIfNotExist(HeaderItems.USER_LOGOUT);// add log out
		if (ui.isAnonymous()) {//
			hv.addItemIfNotExist(HeaderItems.USER_LOGIN);// add log in
		} else {// login
			hv.tryRemoveItem(HeaderItems.USER_LOGIN);
		}
	}
}
