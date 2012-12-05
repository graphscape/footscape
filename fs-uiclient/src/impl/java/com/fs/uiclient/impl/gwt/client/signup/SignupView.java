/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class SignupView extends FormsView implements ManagableI {

	public static String HEADER_ITEM_SIGNUP = "signup";//

	protected ItemModel headerSignupItem;

	private ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public SignupView(String name, ContainerI ctn) {
		super(name, ctn);

	}

	@Override
	public SignupModelI getModel() {
		return (SignupModelI) this.model;
	}

	@Override
	public void doModel(ModelI model) {
		super.doModel(model);

	}

	@Override
	public void doAttach() {
		super.doAttach();

		// TODO move this to the control,this should be a different view from
		// this view.

	}

	protected FrwkModelI getFrwkModel() {
		FrwkModelI fc = this.getModel().getTopObject()
				.getChild(FrwkModelI.class, true);

		return fc;
	}

	// show or hidden this view by model value
	protected void onSignupRequired(ModelValueEvent e) {
		boolean sr = e.getValue(Boolean.FALSE);
		this.managed.select(sr);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagableI#setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ManagedModelI)
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.commons.manage.ManagerModelI.ManagableI
	 * #getManager()
	 */
	@Override
	public String getManager() {

		return BossModelI.M_POPUP;
	}
}
