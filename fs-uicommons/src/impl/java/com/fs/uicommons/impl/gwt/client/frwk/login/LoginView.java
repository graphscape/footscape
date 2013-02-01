package com.fs.uicommons.impl.gwt.client.frwk.login;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * Session view will add user info in the header view.
 * <p>
 * and open the login window for user auth.
 * 
 * @author wu
 * 
 */
public class LoginView extends FormsView implements ViewReferenceI.AwareI {

	// note: this is not the child of this view,

	// it is add to the header view.

	private ViewReferenceI managed;

	private LabelI accountLabel;

	public static String HEADER_ITEM_LOGIN = "login";//

	protected ItemModel headerLoginItem;

	public LoginView(String name, ContainerI ctn, LoginModel lm) {
		super(name, ctn, lm);

		this.accountLabel = this.factory.create(LabelI.class);
		this.accountLabel.parent(this);

	}

	@Override
	public LoginModelI getModel() {
		return (LoginModelI) this.model;
	}

	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);
	}

	/**
	 * @param e
	 */
	protected void onLoginRequired(ModelValueEvent e) {
		this.managed.select(e.getValue(Boolean.FALSE));// show this view's
														// login.

	}

	@Override
	public void doAttach() {
		super.doAttach();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedAwareI#setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
