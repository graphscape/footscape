/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 10, 2013
 */
package com.fs.uiclient.api.gwt.client;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class Actions {
	public static final Path ACTION = ActionEvent.TYPE.getAsPath();

	// user exp
	public static final Path A_USEREXP = ACTION.getSubPath("userexp");

	public static final Path A_UEXP_OPEN_ACTIVITY = A_USEREXP.getSubPath("activity");

	public static final Path A_UEXP_SELECT = A_USEREXP.getSubPath("select");

	public static final Path A_UEXP_COOPER_CONFIRM = A_USEREXP.getSubPath("cooperConfirm");
	// activities

	public static final Path A_ACTS = ACTION.getSubPath("activities");

	public static final Path A_ACTS_ACTIVITIES = A_ACTS.getSubPath("activities");

	// activity
	public static final Path A_ACT = ACTION.getSubPath("activity");

	public static final Path A_ACT_OPEN_CHAT_ROOM = A_ACT.getSubPath("openChatRoom");

	// cooper
	public static final Path A_COOPER = ACTION.getSubPath("cooper");

	public static final Path A_COOP_REQUEST = A_COOPER.getSubPath("request");

	public static final Path A_COOP_CONFIRM = A_COOPER.getSubPath("confirm");

	public static final Path A_COOP_REFRESH_INCOMING_CR = A_COOPER.getSubPath("refreshIncomingCr");
	//

	public static final Path A_EXPE = ACTION.getSubPath("expe");

	public static final Path A_EXPE_SUBMIT = A_EXPE.getSubPath("submit");

	// exps
	public static final Path A_EXPS = ACTION.getSubPath("exps");

	public static final Path A_EXPS_COOPER = A_EXPS.getSubPath("cooper");

	public static final Path A_EXPS_SEARCH = A_EXPS.getSubPath("search");

	// profile
	public static final Path A_PROFILE = ACTION.getSubPath("profile");

	public static final Path A_PROFILE_SUBMIT = A_PROFILE.getSubPath("submit");

	public static final Path A_PROFILE_INIT = A_PROFILE.getSubPath("init");

	// signup
	public static final Path A_SIGNUP = ACTION.getSubPath("signup");

	public static final Path A_SIGNUP_SUBMIT = A_SIGNUP.getSubPath("submit");

	public static final Path A_SIGNUP_CONFIRM = A_SIGNUP.getSubPath("confirm");

	//

	public static final Path A_UELIST = ACTION.getSubPath("uelist");

	public static final Path A_UEL_CREATE = A_UELIST.getSubPath("create");// link
																			// to
																			// new
																			// exp
																			// edit
																			// view.

	public static final Path A_UEL_SELECT = A_UELIST.getSubPath("open");// open
																		// the
																		// default
																		// view
																		// of
																		// the
	// exp,only when the exp is
	// coppered?.
}
