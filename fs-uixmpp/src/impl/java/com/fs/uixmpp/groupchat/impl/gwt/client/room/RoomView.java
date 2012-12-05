/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client.room;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.manage.ManagableI;
import com.fs.uicommons.api.gwt.client.manage.ManagedModelI;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uixmpp.core.api.gwt.client.Jid;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;

/**
 * @author wu
 * 
 */
public class RoomView extends SimpleView implements ManagableI {

	protected ListI occupantList;

	protected ListI messageList;

	protected StringEditorI messageEditor;

	protected ManagedModelI managed;

	/**
	 * @param ctn
	 */
	public RoomView(ContainerI ctn) {
		super(ctn);

		this.occupantList = factory.create(ListI.class);
		this.child(this.occupantList);

		this.messageList = factory.create(ListI.class);
		this.child(this.messageList);// TODO

		this.messageEditor = factory.create(StringEditorI.class);
		this.child(this.messageEditor);//
		messageEditor.getModel().addDefaultValueHandler(
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						RoomView.this.onMessageEditorValue(e);
					}
				});

	}

	public RoomModelI getModel() {
		return (RoomModelI) this.model;
	}

	/**
	 * @param e
	 */
	protected void onMessageEditorValue(ModelValueEvent e) {
		StringData sd = (StringData) e.getValueWrapper().getValue();

		this.getModel().setMessageEditing(
				sd == null ? null : (String) sd.getValue());

	}

	protected void onMessageEditingChange(ChangeEvent<?> e) {
		StringData sd = (StringData) e.getData();
		this.getModel().setProperty(RoomControl.MESSGE_EDITING, sd.getValue());//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.WidgetSupport#doAttach()
	 */
	@Override
	public void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport#processChildModelAdd
	 * (com.fs.uicore.api.gwt.client.ModelI)
	 */
	@Override
	public void processChildModelAdd(ModelI p, ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof OccupantModel) {
			this.processOccupantModelAdd((OccupantModel) cm);
		}
		if (cm instanceof MessageModel) {
			this.processChildMessageModelAdd((MessageModel) cm);
		}
	}

	protected void processChildMessageModelAdd(MessageModel mm) {

		LabelI msgW = this.factory.create(LabelI.class);
		msgW.getModel().setDefaultValue(mm.getDefaultValue());//
		this.messageList.child(msgW);

	}

	protected void processOccupantModelAdd(OccupantModel om) {

		LabelI msgW = this.factory.create(LabelI.class);
		Jid jid = om.getJid();
		if (jid == null) {
			// TODO see RoomControl's presence process.
			//
		}
		msgW.getModel().setDefaultValue(
				om.getNick() + " is joined  with jid" + jid);//

		this.messageList.child(msgW);
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public String getManager() {
		//
		return this.getModel().getManager();
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setManaged(ManagedModelI mgd) {
		this.managed = mgd;
	}
}
