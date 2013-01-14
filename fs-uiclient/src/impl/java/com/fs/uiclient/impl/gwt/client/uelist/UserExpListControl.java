/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.coper.IncomingCrModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpControl;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.DateData;

/**
 * @author wu
 * 
 */
public class UserExpListControl extends ControlSupport implements UserExpListControlI {

	/**
	 * @param name
	 */
	public UserExpListControl(String name) {
		super(name);
		// changing.

	}

	@Override
	protected void doAttach() {
		super.doAttach();

		// listen to the cooper model for incoming cooperrequest.
		MainControlI mc = this.getManager().getControl(MainControlI.class, true);
		Mvc mvc = mc.getLazyObject(MainControlI.LZ_COOPER, true);
		CooperModelI cpm = mvc.getModel();
	}

	public void detailExp(String expId) {
		MsgWrapper req = this.newRequest(Path.valueOf("/uelist/get"));
		req.setPayload("expId", (expId));//

		this.sendMessage(req);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint().sendMessage(req);//
	}

	public MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	@Override
	public UserExpListModelI getModel() {
		return (UserExpListModelI) this.model;
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof UserExpModel) {
			this.processChildUserExpModelAdd((UserExpModel) cm);
		}
	}

	/**
	 * Oct 20, 2012
	 */
	private void processChildUserExpModelAdd(UserExpModel cm) {
		// when new item added,listen to the select value, because zero or only
		// one should be selected.
		// control it

		this.getManager().addControl(new UserExpControl(cm.getName()).model(cm));

	}

	/*
	 * Jan 14, 2013
	 */
	@Override
	public void select(String expId) {

		List<UserExpModel> ueL = this.getModel().getChildList(UserExpModel.class);
		for (UserExpModel ue : ueL) {
			boolean sel = ue.getExpId().equals(expId);
			ue.select(sel);
		}

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
		String expId = cr.getExpId2();// to this exp
		UserExpModel uem = uelm.getUserExp(expId, true);//
		String expId1 = cr.getExpId1();

		uem.setIncomingCrId(crId);// FROM exp id

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
