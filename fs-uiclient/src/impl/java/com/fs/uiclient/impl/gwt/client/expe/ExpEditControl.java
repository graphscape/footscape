/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public class ExpEditControl extends ControlSupport implements ExpEditControlI {

	/**
	 * @param name
	 */
	public ExpEditControl(String name) {
		super(name);
		this.addActionProcessor(ExpEditModelI.A_SUBMIT, new FormDataAP());//
		this.addActionProcessor(ExpEditModelI.A_SUBMIT, new EditSubmitAP());//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.AbstractControl#onActionSuccess
	 * (java.lang.String, com.fs.uicore.api.gwt.client.UiResponse)
	 */
	@Override
	protected void onActionSuccess(String action, UiResponse res) {
		super.onActionSuccess(action, res);
		if (ExpEditModelI.A_SUBMIT.equals(action)) {
			this.onSubmitActionSuccess(res);
		}
	}

	/**
	 * @param res
	 */
	private void onSubmitActionSuccess(UiResponse res) {
		// StringData expId = (StringData)
		// res.getPayloads().getProperty("expId");
		// do nothing,list view will listen
	}

}
