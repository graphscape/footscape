/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wu
 * @deprecated
 */
public class UserLoginEvent extends Event {

	public static final Type<UserLoginEvent> TYPE = new Type<UserLoginEvent>("user-login");

	private UserInfo userInfo;

	/**
	 * @param type
	 */
	public UserLoginEvent(UiObjectI src, UserInfo ui) {
		this(TYPE, src, ui);
	}

	public UserLoginEvent(Type<? extends UserLoginEvent> type, UiObjectI src, UserInfo ui) {
		super(type, src);
		this.userInfo = ui;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

}
