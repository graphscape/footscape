/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.mvc.simple.SimpleView;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 * 
 */
public class ChatGroupView extends SimpleView implements ChatGroupViewI {

	protected ListI participantList;

	protected ListI messageList;

	protected StringEditorI messageEditor;

	protected String groupId;

	protected Map<String, ParticipantModel> pMap;

	protected List<MessageModel> msgList;

	/**
	 * @param ctn
	 */
	public ChatGroupView(ContainerI ctn, String gid) {
		super(ctn, "chatgroup");
		this.groupId = gid;
		this.pMap = new HashMap<String, ParticipantModel>();
		this.msgList = new ArrayList<MessageModel>();
		this.addAction(Actions.A_GCHAT_SEND);
		this.participantList = factory.create(ListI.class);
		this.child(this.participantList);

		this.messageList = factory.create(ListI.class);
		this.child(this.messageList);// TODO

		this.messageEditor = factory.create(StringEditorI.class);
		this.child(this.messageEditor);//

	}

	@Override
	public void addParticipant(ParticipantModel om) {
		// show a message in list.
		LabelI msgW = this.factory.create(LabelI.class);
		String accId = om.getAccountId();
		msgW.setText(om.getNick() + " is joined  with accId:" + accId);//

		this.messageList.child(msgW);
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	public void addMessage(MessageModel msg) {
		this.msgList.add(msg);// cache?

		String text = msg.getText();
		LabelI msgW = this.factory.create(LabelI.class);
		msgW.setText(text);//
		this.messageList.child(msgW);
	}

	/*
	 * Feb 4, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		super.beforeActionEvent(ae);
		ae.setProperty("groupId", this.groupId);
		// for send action
		String text = this.messageEditor.getData();
		ae.setProperty("text", text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI#getMessageModelList
	 * ()
	 */
	@Override
	public List<MessageModel> getMessageModelList() {
		return this.msgList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI#getParticipant(java
	 * .lang.String, boolean)
	 */
	@Override
	public ParticipantModel getParticipant(String pid, boolean force) {
		ParticipantModel rt = this.pMap.get(pid);
		if (rt == null && force) {
			throw new UiException("no participant found:" + pid + " in group:" + this.groupId);
		}
		return rt;
	}

}
