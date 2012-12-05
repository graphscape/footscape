/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class SignupModel extends FormsModel implements SignupModelI {

	public static final String A_SUBMIT = "submit";// signup request

	public static final String A_CONFIRM = "confirm";// signup confirm

	/**
	 * @param name
	 */
	public SignupModel(String name) {
		super(name);
		ControlUtil.addAction(this, A_SUBMIT);
		ControlUtil.addAction(this, A_CONFIRM);
		//

		// form1
		FormModel def = this.getDefaultForm();
		// actions for form1
		def.addAction(A_SUBMIT);

		// fields1
		def.addField("nick", StringData.class);
		def.addField("password", StringData.class);
		def.addField("password2", StringData.class);
		def.addField("email", StringData.class);
		def.addField("isAgree", BooleanData.class);
		// options
		FieldModel fm = def.addField("confirmCodeNotifier", StringData.class,
				EnumEditorI.class, new UiCallbackI<EnumEditorI, Object>() {

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
		con.addAction(A_CONFIRM);
		//
		con.addField("email", StringData.class);
		con.addField("confirmCode", StringData.class);

	}

}
