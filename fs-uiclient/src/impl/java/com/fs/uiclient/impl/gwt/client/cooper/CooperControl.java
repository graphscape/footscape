/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.impl.gwt.client.handler.action.CooperConfirmAP;
import com.fs.uiclient.impl.gwt.client.handler.action.CooperRequestAP;
import com.fs.uiclient.impl.gwt.client.handler.action.RefreshIncomingCrAP;
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
		this.addActionEventHandler(CooperModelI.A_REQUEST, new CooperRequestAP());
		this.addActionEventHandler(CooperModelI.A_CONFIRM, new CooperConfirmAP());
		this.addActionEventHandler(CooperModelI.A_REFRESH_INCOMING_CR, new RefreshIncomingCrAP());

	}

	public MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	@Override
	public CooperModelI getModel() {
		return (CooperModelI) this.model;
	}

	/*
	 *Jan 4, 2013
	 */
	@Override
	public void refreshIncomingCr(String crId) {
		// 
		this.triggerAction(CooperModelI.A_REFRESH_INCOMING_CR);
	}

}
