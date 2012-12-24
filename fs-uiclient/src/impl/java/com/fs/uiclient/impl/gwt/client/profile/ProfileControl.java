/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.simple.FormDataAP;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiResponse;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wuzhen
 * 
 */
public class ProfileControl extends ControlSupport {

	/**
	 * @param client
	 */
	public ProfileControl(String c) {
		super(c);
		this.addActionProcessor(ProfileModelI.A_INIT, new FormInitAP(null,
				"profile"));
		this.addActionProcessor(ProfileModelI.A_SUBMIT, new FormDataAP());

	}

	@Override
	public void doModel(ModelI cm) {
		super.doModel(cm);
		this.model.addValueHandler(Mvc.L_VIEW_OPENED,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ProfileControl.this.onViewOpened(e);
					}
				});
	}

	/**
	 * Nov 17, 2012
	 */
	protected void onViewOpened(ModelValueEvent e) {
		if (!e.getValue(Boolean.FALSE)) {// if is closed.
			return;
		}

		SessionModelI sm = this.model.getTopObject().find(SessionModelI.class,
				true);
		if (!sm.isAuthed()) {
			return;// TODO not allow open or show error in view.
		}

		ControlUtil.triggerAction(this.getModel(), ProfileModelI.A_INIT);//

	}

	public ProfileModel getModel() {
		return (ProfileModel) this.model;
	}

	@Override
	protected void onActionSuccess(String action, UiResponse res) {
		super.onActionSuccess(action, res);
		if (ProfileModelI.A_SUBMIT.equals(action)) {
			this.onActionSubmitSuccess(res);
		}
	}

	protected void onActionSubmitSuccess(UiResponse res) {

	}

}
