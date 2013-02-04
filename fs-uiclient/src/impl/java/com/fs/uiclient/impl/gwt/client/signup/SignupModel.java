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

		// form1
		FormModel def = this.getDefaultForm();
		// actions for form1
		def.addAction(Actions.A_SIGNUP_SUBMIT);

		// fields1
		def.addField("nick", String.class);
		def.addField("password", String.class);
		def.addField("password2", String.class);
		def.addField("email", String.class);
		def.addField("isAgree", Boolean.class);
		// options
		FieldModel fm = def.addField("confirmCodeNotifier", String.class, EnumEditorI.class,
				new UiCallbackI<EnumEditorI, Object>() {

					@Override
					public Object execute(EnumEditorI t) {
						t.addOption("email");
						t.addOption("mobile");//
						t.addOption("resp");//
						return null;
					}
				});

		// form2
		FormModel con = this.addForm(SignupModelI.F_CONFIRM);//
		// actions
		con.addAction(Actions.A_SIGNUP_CONFIRM);
		//
		con.addField("email", String.class);
		con.addField("confirmCode", String.class);

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
