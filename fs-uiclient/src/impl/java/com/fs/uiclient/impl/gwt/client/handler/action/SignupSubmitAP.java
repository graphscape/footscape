/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class SignupSubmitAP extends FormSubmitAP {

	public SignupSubmitAP(ContainerI c) {
		super(c, Path.valueOf("/signup/submit"));
	}

	@Override
	public void handle(ActionEvent ae) {
		MainControlI mc = this.getControl(MainControlI.class, true);
		SignupViewI sv = mc.openSignup();
		sv.clearErrorInfo();

		super.handle(ae);

	}

}
