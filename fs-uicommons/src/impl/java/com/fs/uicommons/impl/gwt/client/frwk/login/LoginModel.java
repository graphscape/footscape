package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.html5.storage.LocalStorageJSO;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

public class LoginModel extends FormsModel implements LoginModelI {

	public static final String FK_SAVINGACCOUNT = "savingAccount";// save in
																	// client
																	// side for
																	// auto
																	// auth.

	public static final String FK_EMAIL = "email";

	public static final String FK_PASSWORD = "password";
	
	private boolean useSavingAccount = true;//NOTE

	public LoginModel(String name) {
		super(name);

		// auth is hidden action
		
		ControlUtil.addAction(this, LoginModelI.A_ANONYMOUS,true);// create anonymous
		
		// client start,to login
		// from cokies or web data

		ControlUtil.addAction(this, LoginModelI.A_LOGOUT);// logout is hidden
															// action

		ControlUtil.addAction(this, LoginModelI.A_SUBMIT);

		FormModel def = this.getDefaultForm();

		def.addField(FK_EMAIL, StringData.class);//
		def.addField(FK_PASSWORD, StringData.class);//
		def.addField(FK_SAVINGACCOUNT, BooleanData.class);
		// actions
		def.addAction(LoginModelI.A_SUBMIT);//
	}

	@Override
	public SessionModelI getSessionModel() {

		return this.getTopObject().getChild(SessionModelI.class, true);
	}

	/*
	 * Dec 9, 2012
	 */
	@Override
	public boolean isSavingAccount() {
		//
		BooleanData bd = (BooleanData) this.getDefaultForm()
				.getFieldModel(FK_SAVINGACCOUNT, true).getFieldValue();
		return bd == null ? false : bd.getValue();

	}

	@Override
	public String getPassword() {
		StringData sd = this.getDefaultForm().getFieldValue(FK_PASSWORD, null);
		return sd == null ? null : sd.getValue();
	}

	/*
	 * Dec 9, 2012
	 */
	@Override
	public String getEmail() {
		//
		StringData sd = this.getDefaultForm().getFieldValue(FK_EMAIL, null);
		return sd == null ? null : sd.getValue();
	}

	/*
	 *Dec 9, 2012
	 */
	@Override
	public boolean getIsUsingSavedAccount() {
		// 
		return this.useSavingAccount;
	}

	/*
	 *Dec 9, 2012
	 */
	@Override
	public void setIsUsingSavedAccout(boolean b) {
		this.useSavingAccount = b;
	}
}
