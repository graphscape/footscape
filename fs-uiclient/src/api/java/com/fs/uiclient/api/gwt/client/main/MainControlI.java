/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.main;

import com.fs.uiclient.api.gwt.client.contactus.ContactUsViewI;
import com.fs.uiclient.api.gwt.client.coper.MyExp;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.profile.ProfileViewI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.user.UserInfoViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.widget.html.HtmlElementWidgetI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Cause;

/**
 * @author wu
 * 
 */
public interface MainControlI extends ControlI {
	public ExpSearchViewI openExpSearch(boolean show);

	public UserExpListViewI openUeList(boolean show);

	public UserInfoViewI openUserInfo(boolean show);
	
	public MyExpViewI openMyExp(Cause cause, String expId, boolean show);

	public SignupViewI openSignup();

	public void closeSignup();

	public ExpEditViewI openExpEditView();

	public ProfileViewI openProfile();

	public void refreshExpConnect(String expId);

	public void refreshExpMessage(Cause cause, String expId);

	public void expDeleted(String expId);

	public void expClosed(String expId);
	
	public void setExpDetail(MyExp me);

	public void refreshUeList();
	
	public void refreshUeList(String expId);

	public void closeLoginView();

	public void closeAll();

	public HtmlElementWidgetI openHtmlResource(Path path, boolean refresh);

	public LoginViewI openLoginView(boolean show);
	
	public ContactUsViewI openContactUsView(boolean show);

}
