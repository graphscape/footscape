/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.activity;

import java.util.List;

import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.ParticipantModel;
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

		ControlUtil.addAction(this, ActivityModelI.A_OPEN_CHAT_ROOM);//
		ControlUtil.addAction(this, ActivityModelI.A_REFRESH);//

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
	public List<ParticipantModel> getParticipantList() {

		//
		return this.getChildList(ParticipantModel.class);

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
	public ParticipantModel getParticipantByExpId(String expId) {
		//
		List<ParticipantModel> pl = this.getParticipantList();
		for (ParticipantModel pi : pl) {
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
	public ParticipantModel addParticipant(String expId, String accId) {
		ParticipantModel rt = new ParticipantModel(expId, expId, accId);
		this.child(rt);
		return rt;
	}

}
