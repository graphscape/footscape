/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.gchat.ParticipantModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatDisconnectedEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.gchat.handler.JoinGMH;
import com.fs.uicommons.impl.gwt.client.gchat.handler.MessageGMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.MessageDispatcherImpl;

/**
 * @author wu
 * @deprecated
 */
public class GChatControlImpl extends ControlSupport implements GChatControlI {

	protected EndPointI endpoint;

	protected MessageDispatcherI dispatcher1;

	protected boolean connected;

	/**
	 * @param name
	 */
	public GChatControlImpl(ContainerI c, String name) {
		super(c, name);

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();

		
		// //
		//
		// // side
		// this.dispatcher1 = new MessageDispatcherImpl("gchat");
		// // dispatcher another:
		// this.endpoint.addHandler(Path.valueOf("/endpoint/message/gchat"),
		// new MessageHandlerI<EndpointMessageEvent>() {
		//
		// @Override
		// public void handle(EndpointMessageEvent t) {
		// Path p = t.getPath();
		// MessageData md = t.getMessage();
		// MessageData md2 = new MessageData(md);
		// md2.setHeader(MessageData.HK_PATH, p.subPath(2).toString());
		// MsgWrapper mw = new MsgWrapper(md2);
		// GChatControlImpl.this.dispatcher1.handle(mw);
		// }
		// });
		//
		// // strict mode
		// this.dispatcher1.addHandler(Path.valueOf(new String[] { "gchat",
		// "join" }), true, new JoinGMH(
		// this.container));
		// // strict mode
		// this.dispatcher1.addHandler(Path.valueOf("/gchat/message"), true, new
		// MessageGMH(this.container));

	}

	@Override
	public void setConnected(boolean c) {
		if (this.connected == c) {
			return;
		}
		this.connected = c;
		if (this.connected) {

			new GChatConnectedEvent(this).dispatch();
		} else {
			new GChatDisconnectedEvent(this).dispatch();

		}

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void join(String gid) {

		MessageData req = new MessageData("/gchat/join");
		req.setHeader("groupId", gid);
		this.endpoint.sendMessage(req);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public boolean isConnected() {
		//
		return this.connected;
	}

	@Override
	public void send(String gid, String text) {

		MessageData req = new MessageData("/gchat/message");
		req.setHeader("groupId", gid);
		req.setHeader("format", "message");//
		MessageData msg = new MessageData("plain-text");
		msg.setHeader("format", "text");
		msg.setPayload("text", (text));
		req.setPayload("message", msg);
		this.endpoint.sendMessage(req);
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	public ChatGroupViewI openChatgroup(final String gid) {
		//

		ChatGroupViewI rt = this.getBodyView().getOrCreateItem(Path.valueOf("chatgroup/" + id),
				new CreaterI<ChatGroupViewI>() {

					@Override
					public ChatGroupViewI create(ContainerI ct) {
						//
						return new ChatGroupView(ct, gid);
					}
				});
		return rt;
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	public void addParticipant(ParticipantModel p) {
		//
		String gid = p.getGroupId();
		ChatGroupViewI v = this.openChatgroup(gid);
		v.addParticipant(p);
	}

	/*
	 * Feb 3, 2013
	 */
	@Override
	public void addMessage(MessageModel mm) {
		String gid = mm.getGroupId();
		ChatGroupViewI v = this.openChatgroup(gid);
		v.addMessage(mm);
	}

}
