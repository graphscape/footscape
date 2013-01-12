/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 10, 2013
 */
package com.fs.uicommons.api.gwt.client;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class Actions {

	public static final Path ACTION = ActionEvent.TYPE.getAsPath();

	public static final Path A_LOGIN = ACTION.getSubPath("login");

	public static final Path A_GCHAT = ACTION.getSubPath("gchat");

	public static final Path A_LOGIN_ANONYMOUS = A_LOGIN.getSubPath("anonymous");
	// create anonymous
	// account.

	public static final Path A_LOGIN_LOGOUT = A_LOGIN.getSubPath("logout");
	// logout and open login
	// view?.
	public static final Path A_LOGIN_AUTO = A_LOGIN.getSubPath("auto");
	
	public static final Path A_LOGIN_SUBMIT = A_LOGIN.getSubPath("submit");
	
	public static final Path A_GCHAT_JOIN = A_GCHAT.getSubPath("join");
	
	public static final Path A_GCHAT_SEND = A_GCHAT.getSubPath("send");

}
