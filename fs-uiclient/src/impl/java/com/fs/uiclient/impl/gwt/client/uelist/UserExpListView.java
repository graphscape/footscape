/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import java.util.List;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public class UserExpListView extends SimpleView implements UserExpListViewI {

	public static final String HEADER_ITEM_USEREXP = "uelist";// my exp msglist

	protected ListI list;

	UserExpListModelI model = new UserExpListModel("ue-msglist");
	EnumEditorI status;

	/**
	 * @param ctn
	 */
	public UserExpListView(ContainerI ctn) {
		super(ctn, "uelist");
		this.addAction(Actions.A_UEL_CREATE, this.actionListInHeader);
		// click and open one exp,enter the exp's main view.
		status = this.factory.create(EnumEditorI.class);
		status.addOption("open");
		status.addOption("close");
		status.addOption("all");
		this.status.setData("open");//
		this.actionListInHeader.child(status);
		status.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>(){

			@Override
			public void handle(ChangeEvent t) {
				// 
				String opt = t.getData();
				
				UserExpListView.this.onStatusChange(opt);
			}});
		
		this.list = this.factory.create(ListI.class);
		this.list.setName("expListContainer");
		this.list.parent(this);
	}
	
	public void onStatusChange(String status){
		this.model.reset();
		this.list.clean();//
		MainControlI mc = this.getControl(MainControlI.class, true);
		
		mc.refreshUeList();
		
	}
	
	@Override
	public String getStatus(){
		String rt = this.status.getData();
		if("all".equals(rt)){
			rt = null;
		}
		return rt;
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
			ue.update(uem);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.UserExpListViewI#getUserExpList()
	 */
	@Override
	public List<UserExpModel> getUserExpList() {
		return this.model.getUelist();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.UserExpListViewI#getUserExp(java.
	 * lang.String, boolean)
	 */
	@Override
	public UserExpModel getUserExp(String id, boolean force) {
		return this.model.getUserExp(id, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.UserExpListViewI#addUserExp(com.fs
	 * .uiclient.api.gwt.client.uexp.UserExpModel)
	 */
	@Override
	public void addUserExp(UserExpModel uem) {
		this.model.addUserExp(uem);
	}

	/*
	 *May 3, 2013
	 */
	@Override
	public void expClosed(String expId) {
		// 
		UserExpItemView ue = this.getUserExpView(expId, false);
		
	}

}
