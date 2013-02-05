/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class SignupView extends FormsView implements SignupViewI {

	private static UiLoggerI LOG = UiLoggerFactory.getLogger(SignupView.class);

	public static String HEADER_ITEM_SIGNUP = "signup";//

	protected ItemModel headerSignupItem;

	private ViewReferenceI managed;

	/**
	 * @param ctn
	 */
	public SignupView(ContainerI ctn) {
		super(ctn, "signup");
		//
		this.addAction(Actions.A_SIGNUP_SUBMIT);
		this.addAction(Actions.A_SIGNUP_CONFIRM);

		// form1
		FormViewI def = this.addForm(FormsViewI.FM_DEFAULT);
		// actions for form1
		def.getFormModel().addAction(Actions.A_SIGNUP_SUBMIT);

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
		FormViewI con = this.addForm(SignupModelI.F_CONFIRM);//
		// actions
		con.getFormModel().addAction(Actions.A_SIGNUP_CONFIRM);
		//
		con.addField("email", String.class);
		con.addField("confirmCode", String.class);
	}

	// show or hidden this view by model value
	protected void onSignupRequired(ModelValueEvent e) {
		boolean sr = e.getValue(Boolean.FALSE);
		this.managed.select(sr);//
	}

	/* (non-Javadoc)
	 * @see com.fs.uiclient.api.gwt.client.signup.SignupViewI#setConfirmCode(java.lang.String)
	 */
	@Override
	public void setConfirmCode(String cc) {
		//TODO
		this.getDefaultFormView().setFieldValue("confirmCode",cc);
	}

}
