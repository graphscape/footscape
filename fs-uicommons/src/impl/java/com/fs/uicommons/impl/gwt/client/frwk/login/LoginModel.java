package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.event.LoginCreatedEvent;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;

public class LoginModel extends FormsModel implements LoginModelI {

	public static final String FK_SAVINGACCOUNT = "savingAccount";// save in
																	// client
																	// side for
																	// auto
																	// auth.

	public static final String FK_EMAIL = "email";

	public static final String FK_PASSWORD = "password";

	// NOTE at the time when client start useing saved account, when open the
	// view of login,set this to false.
	private boolean useSavingAccount = true;

	public LoginModel(String name) {
		super(name);

		ControlUtil.addAction(this, Actions.A_LOGIN_ANONYMOUS, true);// create
																		// anonymous

		// client start,to login
		// from cokies or web data

		ControlUtil.addAction(this, Actions.A_LOGIN_LOGOUT);// logout is hidden
															// action

		ControlUtil.addAction(this, Actions.A_LOGIN_SUBMIT);

		FormModel def = this.getDefaultForm();

		def.addField(FK_EMAIL, String.class);//
		def.addField(FK_PASSWORD, String.class);//
		def.addField(FK_SAVINGACCOUNT, Boolean.class);
		// actions
		def.addAction(Actions.A_LOGIN_SUBMIT);//
	}

	@Override
	protected void doAttach() {
		super.doAttach();
		new LoginCreatedEvent(this).dispatch();
	}

	/*
	 * Dec 9, 2012
	 */
	@Override
	public boolean isSavingAccount() {
		//
		Boolean bd = (Boolean) this.getDefaultForm().getFieldModel(FK_SAVINGACCOUNT, true).getFieldValue();
		return bd == null ? false : bd;

	}

	@Override
	public String getPassword() {
		String sd = this.getDefaultForm().getFieldValue(FK_PASSWORD, null);
		return sd == null ? null : sd;
	}

	/*
	 * Dec 9, 2012
	 */
	@Override
	public String getEmail() {
		//
		String sd = this.getDefaultForm().getFieldValue(FK_EMAIL, null);
		return sd == null ? null : sd;
	}

}
