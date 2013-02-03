/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class ChatGroupView extends SimpleView implements ChatGroupViewI {

	protected ListI participantList;

	protected ListI messageList;

	protected StringEditorI messageEditor;

	protected String groupId;

	/**
	 * @param ctn
	 */
	public ChatGroupView(ContainerI ctn, ChatGroupModel cm) {
		super(Actions.A_GCHAT, "chatgroup", ctn, cm);
		this.groupId = cm.getId();
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
		String sd = (String) e.getValueWrapper().getValue();
		//

	}

	@Override
	public void addParticipant(ParticipantModel om) {
		// show a message in list.
		LabelI msgW = this.factory.create(LabelI.class);
		String accId = om.getAccountId();
		msgW.getModel().setDefaultValue(om.getNick() + " is joined  with accId:" + accId);//

		this.messageList.child(msgW);
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	public void addMessage(String msg) {
		LabelI msgW = this.factory.create(LabelI.class);
		msgW.getModel().setDefaultValue(msg);//
		this.messageList.child(msgW);
	}

}
