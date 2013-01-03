package com.fs.uicommons.api.gwt.client.frwk.login;

import com.fs.uicore.api.gwt.client.ModelI;

public interface LoginModelI extends ModelI {

	public static final String A_ANONYMOUS = "anonymous";// create anonymous
															// account.

	public static final String A_LOGOUT = "logout";// logout and open login
													// view?.

	public static final String A_SUBMIT = "submit";// submit login form

	public static String HEADER_ITEM_LOGIN = "login";//

	public boolean isSavingAccount();// auto auth for next, save successful
	// account/password.

	public String getEmail();

	public String getPassword();

	public boolean getIsUsingSavedAccount();

	public void setIsUsingSavedAccout(boolean b);

}
