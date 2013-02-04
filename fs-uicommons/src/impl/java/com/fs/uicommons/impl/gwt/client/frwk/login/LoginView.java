package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * Session view will add user info in the header view.
 * <p>
 * and open the login window for user auth.
 * 
 * @author wu
 * 
 */
public class LoginView extends FormsView implements LoginViewI {

	// note: this is not the child of this view,

	// it is add to the header view.

	private LabelI accountLabel;

	public static String HEADER_ITEM_LOGIN = "login";//

	protected ItemModel headerLoginItem;

	public LoginView(String name, ContainerI ctn, LoginModel lm) {
		super(name, ctn, lm);

		this.accountLabel = this.factory.create(LabelI.class);
		this.accountLabel.parent(this);

		this.addAction(Actions.A_LOGIN_ANONYMOUS, true);// create
		// anonymous

		// client start,to login
		// from cokies or web data

		this.addAction(Actions.A_LOGIN_LOGOUT);// logout is hidden
		// action

		this.addAction(Actions.A_LOGIN_SUBMIT);

	}

	@Override
	public LoginModelI getModel() {
		return (LoginModelI) this.model;
	}

	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);
	}

	@Override
	public void doAttach() {
		super.doAttach();

	}

}
