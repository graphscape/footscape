/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.main;

import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.profile.ProfileViewI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface MainControlI extends ControlI {
	public ExpSearchViewI openExpSearch(boolean show);
	
	public UserExpListViewI openUeList();

	public MyExpViewI openMyExp(String expId,boolean show);

	public SignupViewI openSignup();
	
	public void closeSignup();

	public ExpEditViewI openExpEditView();

	public ProfileViewI openProfile();

	public void refreshExpConnect(String expId);

	public void refreshExpMessage(String expId);

	public void closeMyExp(String expId);

	public void setExpDetail(String expId, String title);

	public void refreshUeList();

	public void closeLoginView();

	public void closeAll();

}
