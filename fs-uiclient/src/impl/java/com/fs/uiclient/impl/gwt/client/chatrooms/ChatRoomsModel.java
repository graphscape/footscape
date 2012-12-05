/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 22, 2012
 */
package com.fs.uiclient.impl.gwt.client.chatrooms;

import java.util.List;

import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsModelI;
import com.fs.uiclient.impl.gwt.client.chatroom.ChatRoomModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ChatRoomsModel extends ModelSupport implements ChatRoomsModelI {

	/**
	 * @param name
	 */
	public ChatRoomsModel(String name) {
		super(name);
	
		ControlUtil.addAction(this, ChatRoomsModelI.A_OPEN);
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public ChatRoomModelI addChatRoom(String rname, String actId) {
		ChatRoomModelI rt = new ChatRoomModel(rname, actId);//
		this.child(rt);//
		return rt;
	}

	/*
	 * Oct 22, 2012
	 */
	@Override
	public ChatRoomModelI getChatRoom(String actId, boolean force) {
		//
		return this.getChild(ChatRoomModelI.class, actId, force);
	}

	@Override
	public ChatRoomModelI getChatRoomByActivityId(String actId, boolean force) {
		//
		List<ChatRoomModelI> cL = this.getChildList(ChatRoomModelI.class);
		ChatRoomModelI rt = null;
		for (ChatRoomModelI cm : cL) {
			if (cm.isActivityId(actId)) {
				if (rt != null) {
					throw new UiException("too many chat room for activity:"
							+ actId);
				}
				rt = cm;

			}
		}
		if (force && rt == null) {
			throw new UiException("force,no chat room found for activity:"
					+ actId);
		}
		return rt;
	}

}
