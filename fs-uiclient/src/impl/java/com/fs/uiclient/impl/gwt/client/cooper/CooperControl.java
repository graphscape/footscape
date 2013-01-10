/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;

/**
 * @author wu
 * 
 */
public class CooperControl extends ControlSupport implements CooperControlI {

	/**
	 * @param name
	 */
	public CooperControl(String name) {
		super(name);
	
	}

	public MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	@Override
	public CooperModelI getModel() {
		return (CooperModelI) this.model;
	}

	/*
	 * Jan 4, 2013
	 */
	@Override
	public void refreshIncomingCr(String crId) {
		//
		this.triggerAction(Actions.A_COOP_REFRESH_INCOMING_CR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.coper.CooperControlI#cooperConfirm(java
	 * .lang.String)
	 */
	@Override
	public void cooperConfirm(String crId) {
		CooperModelI cm = this.getModel();
		ActionModelI am = cm.getChild(ActionModelI.class, Actions.A_COOP_CONFIRM.getName(), true);
		am.setValue("crId", crId);//
		this.triggerAction(Actions.A_COOP_CONFIRM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.coper.CooperControlI#removeIncomingCr(
	 * java.lang.String)
	 */
	@Override
	public void removeIncomingCr(String crId) {
		this.getModel().removeIncomingCr(crId);
	}

}
