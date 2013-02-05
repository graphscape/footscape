package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * Session view will add user info in the header view.
 * <p>
 * and open the login window for user auth.
 * 
 * @author wu
 * 
 */
public class LoginView extends FormsView implements LoginViewI {

	
	private LabelI accountLabel;

	public static String HEADER_ITEM_LOGIN = "login";//

	public LoginView(ContainerI c, String name) {
		super(c,name);

		this.accountLabel = this.factory.create(LabelI.class);
		this.accountLabel.parent(this);

		this.addAction(Actions.A_LOGIN_ANONYMOUS, true);// create
		// anonymous

		// client start,to login
		// from cokies or web data

		this.addAction(Actions.A_LOGIN_LOGOUT);// logout is hidden
		// action

		this.addAction(Actions.A_LOGIN_SUBMIT);

		FormViewI def = this.addForm("default");

		def.addField(FK_EMAIL, String.class);//
		def.addField(FK_PASSWORD, String.class);//
		def.addField(FK_SAVINGACCOUNT, Boolean.class);
		// actions

	}

	@Override
	public void doAttach() {
		super.doAttach();

	}

}
