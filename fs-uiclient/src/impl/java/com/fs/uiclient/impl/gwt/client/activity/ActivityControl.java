/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import com.fs.uiclient.api.gwt.client.activity.ActivityControlI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ActivityControl extends ControlSupport implements ActivityControlI {

	/**
	 * @param name
	 */
	public ActivityControl(String name) {
		super(name);

	}

	/*
	 * Jan 15, 2013
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();
		this.refresh();
	}

	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);
		if (cm instanceof PartnerModel) {
			this.processChildParticipantModelAdd((PartnerModel) cm);
		}
	}

	@Override
	public ActivityModelI getModel() {
		return super.getModel();
	}

	/**
	 * @param cm
	 */
	private void processChildParticipantModelAdd(PartnerModel cm) {
		//

	}

	/*
	 * Jan 15, 2013
	 */
	@Override
	public void refresh() {

		String actId = this.getModel().getActivityId();

		MsgWrapper req = this.newRequest(Path.valueOf("/activity/refresh"));
		req.setPayload("actId", actId);
		this.sendMessage(req);
	}

}
