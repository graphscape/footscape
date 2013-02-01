/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.IncomingCrModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.ControlSupport2;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.DateData;

/**
 * @author wu
 * 
 */
public class UserExpListControl extends ControlSupport2 implements UserExpListControlI {

	/**
	 * @param name
	 */
	public UserExpListControl(ContainerI c, String name) {
		super(c, name);
		// changing.

	}

	@Override
	protected void doAttach() {
		super.doAttach();

		// listen to the cooper model for incoming cooperrequest.
		MainControlI mc = this.getManager().getControl(MainControlI.class, true);

		CooperModelI cpm = mc.getCooperModel();
	}

	public void detailExp(String expId) {
		MsgWrapper req = this.newRequest(Path.valueOf("/uelist/get"));
		req.setPayload("expId", (expId));//

		this.sendMessage(req);
	}

	public UserExpListModelI getModel() {
		return this.getMainControl().getUeListModel();
	}

	/*
	 * Jan 14, 2013
	 */
	@Override
	public void select(String expId) {

		//model
		List<UserExpModel> ueL = this.getModel().getChildList(UserExpModel.class);
		for (UserExpModel ue : ueL) {
			boolean sel = ue.getExpId().equals(expId);
			ue.select(sel);
		}
		//view
		UserExpListViewI uelv = this.getMainControl().openUeList();
		uelv.select(expId);

		// call search
		ExpSearchControlI sc = this.getManager().getControl(ExpSearchControlI.class, true);
		sc.search(expId);
	}

	/*
	 * Jan 3, 2013
	 */
	@Override
	public void refresh(String expId) {
		UserExpListModel uelm = this.getModel().cast();
		Long lts = uelm.getLastTimestamp(false);
		MsgWrapper req = this.newRequest(Path.valueOf("/uelist/refresh"));
		req.setPayload("lastTimestamp", DateData.valueOf(lts));// fresh from
																// here
		this.sendMessage(req);
	}

	/**
	 * notify a incoming cr.
	 * 
	 */
	@Override
	public void incomingCr(IncomingCrModel cr) {

		UserExpListModelI uelm = this.getModel();
		String crId = cr.getCrId();
		String expId2 = cr.getExpId2();// to this exp

		UserExpModel uem = uelm.getUserExp(expId2, true);//
		String expId1 = cr.getExpId1();// from expId1
		uem.setIncomingCrId(crId);// FROM exp id

		// update view
		UserExpListViewI uev = this.getMainControl().openUeList();
		uev.incomingCr(expId2, crId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI#removeIncomingCr
	 * (java.lang.String)
	 */
	@Override
	public void incomingCrConfirmed(String crId) {
		UserExpListModelI ulm = this.getModel();

		UserExpModel uem = ulm.getUserExpByIncomingCrId(crId, true);
		uem.incomingCrConfirmed(crId);//
	}

}
