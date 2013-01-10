package com.fs.uicommons.api.gwt.client.frwk.login;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.commons.Path;

public interface LoginModelI extends ModelI {

	

	public static String HEADER_ITEM_LOGIN = "login";//

	public boolean isSavingAccount();// auto auth for next, save successful
	// account/password.

	public String getEmail();

	public String getPassword();

	public boolean getIsUsingSavedAccount();

	public void setIsUsingSavedAccout(boolean b);

}
