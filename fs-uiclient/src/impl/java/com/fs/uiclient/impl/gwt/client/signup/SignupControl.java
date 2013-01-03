/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uiclient.impl.gwt.client.handler.action.FormSubmitAP;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wuzhen
 * 
 */
public class SignupControl extends ControlSupport {

	/**
	 * @param client
	 */
	public SignupControl(String c) {
		super(c);
		this.addActionEventHandler(SignupModelI.A_SUBMIT, new FormSubmitAP("/signup/submit"));
		this.addActionEventHandler(SignupModelI.A_CONFIRM, new FormSubmitAP("/signup/confirm",
				SignupModelI.F_CONFIRM));
	}

	@Override
	public ControlI model(ModelI cm) {
		super.model(cm);

		return this;
	}

	public SignupModel getModel() {
		return (SignupModel) this.model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.AbstractControl#doAttach()
	 */
	@Override
	protected void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();

	}

}
