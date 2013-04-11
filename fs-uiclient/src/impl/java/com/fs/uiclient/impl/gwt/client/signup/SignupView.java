/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
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

		ButtonI fb = this.addAction(Actions.A_SIGNUP_FBLOGIN);
		fb.getElement().addClassName("facebook-login-button");
		// form1
		FormViewI def = this.getDefaultForm();
		// actions for form1
		def.getFormModel().addAction(Actions.A_SIGNUP_SUBMIT);
		def.getFormModel().addAction(Actions.A_SIGNUP_FBLOGIN);

		// fields1
		def.addField("nick", String.class);
		Map<String,Object> pts= new HashMap<String,Object>();
		pts.put(StringEditorI.PK_ISPASSWORD, Boolean.TRUE);
		def.addField("password", String.class, pts);
		def.addField("email", String.class);

	}

	// show or hidden this view by model value
	protected void onSignupRequired(ModelValueEvent e) {
		boolean sr = e.getValue(Boolean.FALSE);
		this.managed.select(sr);//
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public String getEmail() {
		//
		FormViewI def = this.getDefaultForm();
		return def.getFieldData("email");
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public String getPassword() {
		FormViewI def = this.getDefaultForm();
		return def.getFieldData("password");
	}

}
