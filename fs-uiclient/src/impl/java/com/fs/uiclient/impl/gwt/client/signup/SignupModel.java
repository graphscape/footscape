/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.event.model.SignupConfirmCodeEvent;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;

/**
 * @author wu
 * 
 */
public class SignupModel extends FormsModel implements SignupModelI {

	private String confirmCode;

	/**
	 * @param name
	 */
	public SignupModel(String name) {
		super(name);
		
		//

		

	}

	/*
	 * Jan 12, 2013
	 */
	@Override
	public void setConfirmCode(String cc) {
		this.confirmCode = cc;
		new SignupConfirmCodeEvent(this, cc).dispatch();

	}

}
