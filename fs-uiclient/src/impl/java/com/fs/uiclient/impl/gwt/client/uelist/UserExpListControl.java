/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
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

		// model
		List<UserExpModel> ueL = this.getModel().getChildList(UserExpModel.class);
		for (UserExpModel ue : ueL) {
			boolean sel = ue.getExpId().equals(expId);
			ue.select(sel);
		}
		// view
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

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void addOrUpdateUserExp(UserExpModel uem) {
		String id = uem.getExpId();
		UserExpListModelI ulm = this.getModel();
		UserExpModel old = ulm.getUserExp(id, false);
		if (old == null) {
			ulm.addUserExp(uem);
		} else {
			old.setBody(uem.getBody());
		}
		UserExpListViewI uelv = this.getMainControl().openUeList();
		uelv.update(uem);
	}

	/*
	 *Mar 6, 2013
	 */
	@Override
	public void addOrUpdateExpMessage(ExpMessage msg) {
		//get or open the ExpView
		String expId = msg.getExpId2();
		MyExpViewI me = this.openMyExpView(expId);
		me.addOrUpdateMessage(msg);
	}

	/**
	 *Mar 6, 2013
	 */
	private MyExpViewI openMyExpView(String expId) {
		//
		MyExpViewI rt = this.getMainControl().openMyExp(expId);
		
		return rt;
	}

}
