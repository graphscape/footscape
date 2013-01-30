/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.signup;

import com.fs.uiclient.api.gwt.client.event.model.SignupConfirmCodeEvent;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;

/**
 * @author wu
 * 
 */
public class SignupView extends FormsView implements ViewReferenceI.AwareI {

	private static UiLoggerI LOG = UiLoggerFactory.getLogger(SignupView.class);

	public static String HEADER_ITEM_SIGNUP = "signup";//

	protected ItemModel headerSignupItem;

	private ViewReferenceI managed;

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
		model.addHandler(SignupConfirmCodeEvent.TYPE, new EventHandlerI<SignupConfirmCodeEvent>() {

			@Override
			public void handle(SignupConfirmCodeEvent t) {
				SignupView.this.onSignupConfirmCodeEvent(t);
			}
		});
	}

	/**
	 * Jan 12, 2013
	 */
	protected void onSignupConfirmCodeEvent(SignupConfirmCodeEvent t) {
		String cc = t.getConfirmCode();
		LOG.info("confirm code got : " + cc);
		if (cc == null) {
			return;
		}
		// TODO popup view?
	}

	@Override
	public void doAttach() {
		super.doAttach();

		// TODO move this to the control,this should be a different view from
		// this view.

	}

	protected FrwkModelI getFrwkModel() {
		FrwkModelI fc = this.getModel().getTopObject().getChild(FrwkModelI.class, true);

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
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI.AwareI
	 * #setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
