package com.fs.uicommons.impl.gwt.client.frwk.password;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.password.PasswordResetViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicommons.impl.gwt.client.frwk.login.RegisteredAccountLDW;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * Session view will add user info in the header view.
 * <p>
 * and open the login window for user auth.
 * 
 * @author wu
 * 
 */
public class PasswordResetView extends FormsView implements PasswordResetViewI {

	public PasswordResetView(ContainerI c) {
		super(c);

		this.addAction(Actions.A_PASSWORD_RESET);

		FormViewI def = this.getDefaultForm();
		def.addField(FK_PFID, String.class);//
		def.addField(FK_NEWPASSWORD, String.class);//

	}

	@Override
	public void doAttach() {
		super.doAttach();

	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void setPfId(String pfId) {
		//
		FormViewI def = this.getDefaultForm();
		def.setFieldValue(FK_PFID, pfId);
	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public String getPfId() {
		//
		return this.getDefaultForm().getFieldData(FK_PFID);

	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public String getNewPassword() {
		//
		return this.getDefaultForm().getFieldData(FK_NEWPASSWORD);

	}

}
