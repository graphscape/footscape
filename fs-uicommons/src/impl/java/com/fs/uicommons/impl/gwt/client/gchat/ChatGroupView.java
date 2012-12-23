/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ChatGroupView extends SimpleView {

	protected ListI participantList;

	protected ListI messageList;

	protected StringEditorI messageEditor;

	/**
	 * @param ctn
	 */
	public ChatGroupView(ContainerI ctn) {
		super(ctn);

		this.participantList = factory.create(ListI.class);
		this.child(this.participantList);

		this.messageList = factory.create(ListI.class);
		this.child(this.messageList);// TODO

		this.messageEditor = factory.create(StringEditorI.class);
		this.child(this.messageEditor);//
		messageEditor.getModel().addDefaultValueHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				ChatGroupView.this.onMessageEditorValue(e);
			}
		});

	}

	public ChatGroupModel getModel() {
		return (ChatGroupModel) this.model;
	}

	/**
	 * @param e
	 */
	protected void onMessageEditorValue(ModelValueEvent e) {
		StringData sd = (StringData) e.getValueWrapper().getValue();

		this.getModel().setMessageEditing(sd == null ? null : (String) sd.getValue());

	}

	protected void onMessageEditingChange(ChangeEvent<?> e) {
		StringData sd = (StringData) e.getData();
		// this.getModel().setProperty(RoomControl.MESSGE_EDITING,
		// sd.getValue());//
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
		if (cm instanceof ParticipantModel) {
			this.processParticipantModelAdd((ParticipantModel) cm);
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

	protected void processParticipantModelAdd(ParticipantModel om) {
		// show a message in list.
		LabelI msgW = this.factory.create(LabelI.class);
		String accId = om.getAccountId();
		msgW.getModel().setDefaultValue(om.getNick() + " is joined  with accId:" + accId);//

		this.messageList.child(msgW);
	}

}
