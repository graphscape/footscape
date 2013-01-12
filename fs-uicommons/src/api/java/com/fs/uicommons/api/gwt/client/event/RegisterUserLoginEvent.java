/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wu
 * 
 */
public class RegisterUserLoginEvent extends UserLoginEvent {

	public static final Type<RegisterUserLoginEvent> TYPE = new Type<RegisterUserLoginEvent>(UserLoginEvent.TYPE,"registered");

	/**
	 * @param type
	 */
	public RegisterUserLoginEvent(LoginControlI src, UserInfo ui) {
		super(TYPE, src, ui);
	}

}
