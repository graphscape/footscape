package com.fs.uicommons.api.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;

public interface LoginModelI extends ModelI {

	public static final String A_AUTH = "auth";// do auth at client start.

	public static final String A_LOGOUT = "logout";// logout and open login view?.

	public static final String A_SUBMIT = "submit";// submit login form

	public static String HEADER_ITEM_LOGIN = "login";//
	
	public static final String L_ACCOUNTID_SAVED = "accountIdSaved";

	public static final String L_PASSWORD_SAVED = "passwordSaved";

	public SessionModelI getSessionModel();// find
	
	public void saveAccountAndPassword(String accId,String password);
	
	public String getAccountIdSaved();
	
	public String getPasswordSaved();

}
