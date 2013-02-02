/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import java.util.ArrayList;
import java.util.List;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class UserExpListModel extends ModelSupport implements UserExpListModelI {

	/**
	 * @param name
	 */
	public UserExpListModel(String name) {
		super(name);
		// new open the other view:ExpEditModel.
		ControlUtil.addAction(this, Actions.A_UEL_CREATE);
		// click and open one exp,enter the exp's main view.

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void addUserExp(UserExpModel uem) {
		uem.parent(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI#getUserExp(java
	 * .lang.String, boolean)
	 */
	@Override
	public UserExpModel getUserExp(String id, boolean force) {

		return this.getChild(UserExpModel.class, id, force);

	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public UserExpModel getSelected(boolean force) {
		//
		List<UserExpModel> rtL = this.getChildList(UserExpModel.class);
		for (UserExpModel uem : rtL) {
			if (uem.isSelected()) {
				return uem;
			}
		}
		if (force) {
			throw new UiException("no selected exp in user exp list:" + rtL);
		}
		return null;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void select(String expId) {
		UserExpModel rt = null;
		List<UserExpModel> ueL = this.getChildList(UserExpModel.class);
		for (UserExpModel ue : ueL) {
			if (ue.isExpId(expId)) {
				rt = ue;
				if (!ue.isSelected()) {
					ue.select(true);
				}
			} else {
				if (ue.isSelected()) {
					ue.select(false);
				}
			}
		}
		if (rt == null) {
			throw new UiException("no this expId:" + expId + " in " + this);//
		}
		this.setValue(L_SELECTED_EXP_ID, expId);
	}

	/**
	 * @return
	 */
	public Long getLastTimestamp(boolean force) {
		Long rt = null;
		List<UserExpModel> ueL = this.getChildList(UserExpModel.class);
		for (UserExpModel ue : ueL) {
			DateData ts = ue.getTimestamp(true);
			if (rt == null || rt < ts.getValue()) {
				rt = ts.getValue();
			}
		}
		if (force && rt == null) {
			throw new UiException("no items found");
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI#getUserExpByActivityId
	 * (java.lang.String, boolean)
	 */
	@Override
	public List<UserExpModel> getUserExpByActivityId(String actId) {
		List<UserExpModel> ueL = this.getChildList(UserExpModel.class);
		List<UserExpModel> rt = new ArrayList<UserExpModel>();
		for (UserExpModel ue : ueL) {
			if (actId.equals(ue.getActivityId())) {
				rt.add(ue);
			}
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI#
	 * getUserExpByIncomingCrId(java.lang.String, boolean)
	 */
	@Override
	public UserExpModel getUserExpByIncomingCrId(String crId, boolean force) {
		List<UserExpModel> ueL = this.getChildList(UserExpModel.class);
		List<UserExpModel> rt = new ArrayList<UserExpModel>();
		for (UserExpModel ue : ueL) {
			if (crId.equals(ue.getIncomingCrId())) {
				rt.add(ue);
			}
		}
		if (rt.isEmpty()) {
			if (force) {
				throw new UiException("no user exp with incoming cr:" + crId + ",all userexp:" + ueL);
			}
			return null;
		} else if (rt.size() > 1) {
			throw new UiException("to many user exp for crId:" + crId + ", all:" + rt);
		} else {
			return rt.get(0);

		}
	}

}
