/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class UserExpListView extends SimpleView implements UserExpListViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp list

	protected ListI list;

	/**
	 * @param ctn
	 */
	public UserExpListView(ContainerI ctn, UserExpListModelI uem) {
		super(Actions.A_UELIST, "uelist", ctn, uem);

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
		UserExpView ue = new UserExpView(expId, this.getContainer(), cm);
		ue.parent(this.list);

	}

	public UserExpView getUserExpView(String expId, boolean force) {
		return this.list.find(UserExpView.class, expId, force);

	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void incomingCr(String expId, String crId) {
		UserExpView ue = this.getUserExpView(expId, true);
		ue.update();
	}

	/*
	 * Feb 1, 2013
	 */
	@Override
	public void select(String expId) {
		UserExpView ue = this.getUserExpView(expId, true);
		ue.update();
	}

}
