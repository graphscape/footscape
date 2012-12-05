package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.html5.storage.LocalStorageJSO;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

public class LoginModel extends FormsModel implements LoginModelI {

	public LoginModel(String name) {
		super(name);

		// auth is hidden action
		ControlUtil.addAction(this, LoginModelI.A_AUTH);// trying auto auth when
		// client start,to login
		// from cokies or web data

		ControlUtil.addAction(this, LoginModelI.A_LOGOUT);// logout is hidden
															// action

		ControlUtil.addAction(this, LoginModelI.A_SUBMIT);

		FormModel def = this.getDefaultForm();

		def.addField("email", StringData.class);//
		def.addField("password", StringData.class);//
		// actions
		def.addAction(LoginModelI.A_SUBMIT);//
	}

	@Override
	public SessionModelI getSessionModel() {

		return this.getTopObject().getChild(SessionModelI.class, true);
	}

	/*
	 * Nov 25, 2012
	 */
	@Override
	public String getAccountIdSaved() {// TODO get from client data.
		//
		String rt = (String) this.getValue(LoginModelI.L_ACCOUNTID_SAVED);
		if (rt != null) {// the saved value already read into model
			return rt;
		}
		//read from client storage
		LocalStorageJSO lsj = LocalStorageJSO.getInstance();
		rt = lsj.getValue(LoginModelI.L_ACCOUNTID_SAVED);
		
		return rt;
		
	}

	/*
	 * Nov 25, 2012
	 */
	@Override
	public String getPasswordSaved() {
		String rt = (String) this.getValue(LoginModelI.L_PASSWORD_SAVED);
		//
		if (rt != null) {// the saved value already read into model
			return rt;
		}
		LocalStorageJSO lsj = LocalStorageJSO.getInstance();
		rt = lsj.getValue(LoginModelI.L_PASSWORD_SAVED);
		
		return rt;
	}

	@Override
	public void saveAccountAndPassword(String accId, String password) {
		LocalStorageJSO lsj = LocalStorageJSO.getInstance();
		lsj.setValue(LoginModelI.L_ACCOUNTID_SAVED,accId);
		lsj.setValue(LoginModelI.L_PASSWORD_SAVED,password);
		
		this.setValue(LoginModelI.L_ACCOUNTID_SAVED, accId);
		this.setValue(LoginModelI.L_PASSWORD_SAVED, password);//
		
	}
}
