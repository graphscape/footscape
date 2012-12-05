/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.rooms;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.manage.util.BossUtil;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.impl.gwt.client.room.RoomView;

/**
 * @author wu
 * 
 */
public class RoomsView extends SimpleView implements ManagableI {

	protected TabberWI rooms;

	protected StringEditorI roomNameEditor;

	protected ManagedModelI managed;

	public static final String TESTING_ROOM_NAME_EDITOR = "roomNameEditor";

	/**
	 * @param ctn
	 */
	public RoomsView(String name, ContainerI ctn) {
		super(name, ctn);
		this.rooms = factory.create(TabberWI.class);
		this.child(this.rooms);// TODO

		this.roomNameEditor = this.factory.create(StringEditorI.class);
		this.roomNameEditor.setName(TESTING_ROOM_NAME_EDITOR);// FOR testing to
																// find this
																// widget.
		this.roomNameEditor.getModel().addDefaultValueHandler(
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						RoomsView.this.onNameChange(e);
					}
				});
		this.child(this.roomNameEditor);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);

	}

	protected void onNameChange(ModelValueEvent e) {
		StringData sd = (StringData) e.getValueWrapper().getValue();

		this.model.setValue(RoomsModel.L_ROOMNAME_EDITING, sd.getValue());
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
		if (cm instanceof RoomModelI) {
			this.processChildRoomModelAdd((RoomModelI) cm);
		}
	}

	/**
	 * Oct 21, 2012
	 */
	private void processChildRoomModelAdd(RoomModelI cm) {
		RoomView rv = new RoomView(this.getContainer());
		rv.setName(cm.getName());
		rv.model(cm);//
		// TODO use the uniform manager,for instance to register manager in
		// framework.
		// and the manager actually is maintained by the roomsview.
		String viewM = rv.getManager();
		if (viewM == null) {
			String rname = cm.getName();
			// TODO managed by FrwkModelI
			TabWI tab = this.rooms.addTab(rname, rv);
		} else {
			BossUtil.manage(cm, rv);
		}
	}

}
