/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.handler.gchat;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.annotation.Handle;
import com.fs.gridservice.commons.api.data.EventGd;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.gchat.ChatGroupI;
import com.fs.gridservice.commons.api.gchat.ChatGroupManagerI;
import com.fs.gridservice.commons.api.wrapper.WsMsgReceiveEW;
import com.fs.gridservice.commons.api.wrapper.WsMsgSendEW;
import com.fs.gridservice.commons.impl.support.WsMsgReseiveEventHandler;
import com.fs.gridservice.core.api.objects.DgQueueI;

/**
 * @author wu
 *
 */
public class GroupChatJoinEventHandler  extends WsMsgReseiveEventHandler {

	protected ChatGroupManagerI chatGroupManager;

	/*
	 * Dec 16, 2012
	 */
	@Handle("join")
	// message from one of participant,websocket, dispatch to other
	// participants.
	public void handleJoin(WsMsgReceiveEW reqE, WsMsgSendEW resE,
			RequestI req) {
		//
		MessageI msg = reqE.getMessage();
		String sid = (String) msg.getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);
		if (s == null) {// TODO event,code?
			throw new FsException("TODO");
		}
		String wsoId = reqE.getWebSocketId();
		if (wsoId == null) {
			throw new FsException("TODO");
		}

		String gId = (String)msg.getPayload("groupId",true);
		
		ChatGroupI cr = this.chatGroupManager.getChatGroup(gId);
		if(cr == null){
			PropertiesI<Object> pts = new MapProperties<Object>();//TODO
			cr = this.chatGroupManager.createChatRoom(pts);
		}
		
		// TODO move to uper class.
		MessageI m2 = new MessageSupport();
		m2.setHeader("path", "/gchat/status/join");//
		// TODO get the queue of the member that contains the web socket
		String mid = this.facade.getLocalMember().getId();

		DgQueueI<EventGd> mqueue = this.facade.getMemberEventQueue(mid);

		WsMsgSendEW ok = WsMsgSendEW.valueOf("/wsmsg/send", wsoId, m2);

		mqueue.offer(ok.getTarget());

	}

}
