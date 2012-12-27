/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.util.BossUtil;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.event.SelectEvent;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class GChatView extends SimpleView implements ManagableI {

	protected TabberWI groups;

	protected StringEditorI groupIdEditor;

	protected ManagedModelI managed;

	public static final String TESTING_ROOM_NAME_EDITOR = "groupIdEditor";

	/**
	 * @param ctn
	 */
	public GChatView(String name, ContainerI ctn) {
		super(name, ctn);
		this.groups = factory.create(TabberWI.class);
		this.child(this.groups);// TODO

		this.groupIdEditor = this.factory.create(StringEditorI.class);
		this.groupIdEditor.setName(TESTING_ROOM_NAME_EDITOR);// FOR testing to
																// find this
																// widget.
		this.groupIdEditor.getModel().addDefaultValueHandler(
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						GChatView.this.onGroupIdChange(e);
					}
				});
		this.child(this.groupIdEditor);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);

	}

	@Override
	public GChatModel getModel() {
		return (GChatModel) this.model;
	}

	protected void onGroupIdChange(ModelValueEvent e) {
		StringData sd = (StringData) e.getValueWrapper().getValue();
		String gid = sd == null ? null : sd.getValue();
		this.getModel().setGroupIdToJoin(gid);//
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public String getManager() {
		//

		return BossModelI.M_LEFT; // TODO popup manager.

	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof ChatGroupModel) {
			this.processChildGroupModelAdd((ChatGroupModel) cm);
		}
	}

	/**
	 * Oct 21, 2012
	 */
	private void processChildGroupModelAdd(ChatGroupModel cm) {
		ChatGroupView rv = new ChatGroupView(this.getContainer());
		rv.setName(cm.getName());
		rv.model(cm);//
		final String gid = cm.getName();
		// TODO use the uniform manager,for instance to register manager in
		// framework.
		// and the manager actually is maintained by the roomsview.
		String viewM = null;// open the group view in this view.
		if (viewM == null) {
			String rname = cm.getName();
			// TODO managed by FrwkModelI
			TabWI tab = this.groups.addTab(rname, rv);
			this.getModel().setCurrentGroupId(gid);//
			tab.addSelectEventHandler(new EventHandlerI<SelectEvent>() {

				@Override
				public void handle(SelectEvent t) {
					if(t.isSelected()){
						GChatView.this.getModel().setCurrentGroupId(gid);//
					}
				}
			});
		} else {
			BossUtil.manage(cm, rv);
		}
	}

}
