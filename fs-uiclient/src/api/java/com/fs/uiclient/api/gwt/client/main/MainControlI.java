/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.main;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface MainControlI extends ControlI {

	public ExpSearchModelI getExpSearchModel();

	public ExpEditModelI getExpEditModel();

	public CooperModelI getCooperModel();

	public UserExpListModelI getUeListModel();

	public SignupModelI getSignupModel();

	public ExpSearchViewI openExpSearch();

	public UserExpListViewI openUeList();

	public SignupViewI openSignup();
	
	public ExpEditViewI openExpEditView();

	public void openProfile();

}
