/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.main;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface MainControlI extends ControlI {

	public ExpSearchModelI getExpSearchModel();

	public ExpEditModelI getExpExitModel();

	public CooperModelI getCooperModel();

	public void openExpSearch();

	public void openUeList();

	public SignupView getSignupView();

}
