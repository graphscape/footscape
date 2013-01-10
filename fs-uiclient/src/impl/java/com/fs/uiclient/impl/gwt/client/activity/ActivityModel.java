/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import java.util.List;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.PartnerModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ActivityModel extends ModelSupport implements ActivityModelI {

	/**
	 * @param name
	 */
	public ActivityModel(String name, String id) {
		super(name, id);//

		ControlUtil.addAction(this, Actions.A_ACT_OPEN_CHAT_ROOM);//
		ControlUtil.addAction(this, Actions.A_ACT_REFRESH);//

		this.setActivityId(id);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public String getActivityId() {
		//
		return this.getValue(String.class, L_ACTIVITY_ID);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public List<PartnerModel> getParticipantList() {

		//
		return this.getChildList(PartnerModel.class);

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void setActivityId(String actId) {
		this.setValue(L_ACTIVITY_ID, actId);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void select() {
		this.setValue(L_SELECTED, true);//
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public boolean isSelected() {
		//
		return this.getValue(Boolean.class, L_SELECTED, Boolean.FALSE);

	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public PartnerModel getParticipantByExpId(String expId) {
		//
		List<PartnerModel> pl = this.getParticipantList();
		for (PartnerModel pi : pl) {
			if (pi.isExpId(expId)) {
				return pi;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.activity.ActivityModelI#addParticipant
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public PartnerModel addParticipant(String expId, String accId) {
		PartnerModel rt = new PartnerModel(expId, expId, accId);
		this.child(rt);
		return rt;
	}

}
