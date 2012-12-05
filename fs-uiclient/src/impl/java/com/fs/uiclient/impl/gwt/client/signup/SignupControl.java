/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

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
		this.addActionProcessor(SignupModelI.A_SUBMIT, new FormDataAP());
		this.addActionProcessor(SignupModelI.A_CONFIRM, new FormDataAP(
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

	@Override
	protected void onActionSuccess(String action, UiResponse res) {
		super.onActionSuccess(action, res);
		if (SignupModel.A_SUBMIT.equals(action)) {
			this.onActionSubmitSuccess(res);
		}
	}

	protected void onActionSubmitSuccess(UiResponse res) {

		StringData sid = (StringData) res.getPayloads().getProperty(
				"confirmCode");// for 'resp' type of confirm code notifier

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
