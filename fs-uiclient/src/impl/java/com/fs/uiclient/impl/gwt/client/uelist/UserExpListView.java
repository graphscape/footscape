/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpListView extends SimpleView implements UserExpListViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	protected ListI list;

	UserExpListModelI model;

	/**
	 * @param ctn
	 */
	public UserExpListView(ContainerI ctn, UserExpListModelI uem) {
		super(ctn, "uelist");
		this.model = uem;
		this.addAction(Actions.A_UEL_CREATE);
		// click and open one exp,enter the exp's main view.

		this.list = this.factory.create(ListI.class);
		this.list.setName("expListContainer");
		this.list.parent(this);
		for (UserExpModel ue : uem.getChildList(UserExpModel.class)) {
			this.addUserExpModel(ue);
		}
	}

	/**
	 * @param cm
	 */
	public void addUserExpModel(UserExpModel cm) {
		String expId = cm.getExpId();
		UserExpItemView ue = new UserExpItemView(this.getContainer(), expId, cm);
		ue.parent(this.list);

	}

	public UserExpItemView getUserExpView(String expId, boolean force) {
		return this.list.find(UserExpItemView.class, expId, force);

	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void incomingCr(String expId, String crId) {
		UserExpItemView ue = this.getUserExpView(expId, true);
		ue.update();
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void select(String expId) {
		UserExpItemView ue = this.getUserExpView(expId, true);
		ue.update();
	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void update(UserExpModel uem) {
		String expId = uem.getExpId();
		UserExpItemView ue = this.getUserExpView(expId, false);
		if (ue == null) {
			this.addUserExpModel(uem);
		} else {
			ue.update();
		}

	}

	/*
	 * Mar 16, 2013
	 */
	@Override
	public void delete(String expId) {
		//
		UserExpItemView ue = this.getUserExpView(expId, false);
		ue.parent(null);
	}

}
