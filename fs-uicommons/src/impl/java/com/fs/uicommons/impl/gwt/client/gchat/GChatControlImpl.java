/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatDisconnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatGroupCreatedEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.impl.gwt.client.gchat.handler.JoinGMH;
import com.fs.uicommons.impl.gwt.client.gchat.handler.MessageGMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.MessageDispatcherImpl;

/**
 * @author wu
 * 
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

		this.endpoint = this.getClient(true).getChild(EndPointI.class, true);
		//

		// side
		this.dispatcher1 = new MessageDispatcherImpl("gchat");
		// dispatcher another:
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/gchat"),
				new MessageHandlerI<EndpointMessageEvent>() {

					@Override
					public void handle(EndpointMessageEvent t) {
						Path p = t.getPath();
						MessageData md = t.getMessage();
						MessageData md2 = new MessageData(md);
						md2.setHeader(MessageData.HK_PATH, p.subPath(2).toString());
						MsgWrapper mw = new MsgWrapper(md2);
						GChatControlImpl.this.dispatcher1.handle(mw);
					}
				});

		// strict mode
		this.dispatcher1.addHandler(Path.valueOf(new String[] { "gchat", "join" }), true, new JoinGMH(this));
		// strict mode
		this.dispatcher1.addHandler(Path.valueOf("/gchat/message"), true, new MessageGMH(this));

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
		GChatModel m = this.getOrCreateChatModel();
		m.setGroupIdToJoin(gid);
		this.join();
	}

	@Override
	public void join() {
		//
		GChatModel m = this.getOrCreateChatModel();
		String gid = m.getGroupIdToJoin(true);
		ChatGroupModel gm = this.getOrCreateGroup(gid);
		MessageData req = new MessageData("/gchat/join");
		req.setHeader("groupId", gid);
		this.endpoint.sendMessage(req);
	}

	public ChatGroupModel getOrCreateGroup(String gid) {
		GChatModel m = this.getOrCreateChatModel();
		ChatGroupModel gm = m.getGroup(gid, false);
		if (gm == null) {//
			gm = m.createGroup(gid);
			new GChatGroupCreatedEvent(this, gid).dispatch();
		}
		return gm;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public GChatModel getOrCreateChatModel() {
		//
		ModelI root = this.getClient(true).getRootModel();
		GChatModel rt = root.find(GChatModel.class, false);
		if (rt == null) {
			rt = new GChatModel("gchat");
			rt.parent(root);
		}
		return rt;

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
		GChatModel m = this.getOrCreateChatModel();
		m.setCurrentGroupId(gid);
		ChatGroupModel gm = this.getOrCreateChatModel().getGroup(gid, true);
		gm.setMessageToSend(text);
		this.send();
	}

	@Override
	public void send() {
		String cid = this.getOrCreateChatModel().getCurrentGroupId();
		if (cid == null) {
			throw new UiException("please setCurrentGroupId() before send message");
		}
		ChatGroupModel gm = this.getOrCreateChatModel().getGroup(cid, true);
		String text = gm.getMessageToSend();
		if (text == null) {
			throw new UiException("message to send is null");
		}
		MessageData req = new MessageData("/gchat/message");
		req.setHeader("groupId", cid);
		req.setHeader("format", "message");//
		MessageData msg = new MessageData("plain-text");
		msg.setHeader("format", "text");
		msg.setPayload("text", (text));
		req.setPayload("message", msg);
		this.endpoint.sendMessage(req);
	}

}
