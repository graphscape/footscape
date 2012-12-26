/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointBondEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointCloseEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointOpenEvent;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatConnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatDisconnectedEvent;
import com.fs.uicommons.api.gwt.client.gchat.event.GChatGroupCreatedEvent;
import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMvcSupport;
import com.fs.uicommons.impl.gwt.client.gchat.handler.JoinGMH;
import com.fs.uicommons.impl.gwt.client.gchat.handler.MessageGMH;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class GChatControlImpl extends ControlSupport implements GChatControlI {

	protected EndPointI endpoint;

	protected MessageDispatcherI dispatcher0;

	protected MessageDispatcherI dispatcher1;

	protected boolean connected;

	/**
	 * @param name
	 */
	public GChatControlImpl(String name) {
		super(name);
		this.localMap.put(GChatModel.A_JOIN, true);
		this.localMap.put(GChatModel.A_SEND, true);
		this.addActionProcessor(GChatModel.A_JOIN, new JoinAP());
		this.addActionProcessor(GChatModel.A_SEND, new SendAP());

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

		this.endpoint.addHandler(EndpointOpenEvent.TYPE,
				new EventHandlerI<EndpointOpenEvent>() {

					@Override
					public void handle(EndpointOpenEvent t) {
						GChatControlImpl.this.onEndpointOpen(t);
					}
				});

		this.endpoint.addHandler(EndpointCloseEvent.TYPE,
				new EventHandlerI<EndpointCloseEvent>() {

					@Override
					public void handle(EndpointCloseEvent t) {
						GChatControlImpl.this.onEndpointClose(t);
					}
				});

		this.endpoint.addHandler(EndpointBondEvent.TYPE,
				new EventHandlerI<EndpointBondEvent>() {

					@Override
					public void handle(EndpointBondEvent t) {
						GChatControlImpl.this.onEndpointAuth(t);
					}
				});

		if (this.endpoint.isBond()) {
			this.setConnected(true);//
		}
		//

		MessageDispatcherI.FactoryI df = this.getClient(true).getChild(
				MessageDispatcherI.FactoryI.class, true);
		this.dispatcher0 = df.get(EndPointI.D_NAME);// this is endpoint message
													// from server
		// side
		this.dispatcher1 = df.get(GChatControlI.D_NAME);// this is for gchat sub
														// message type.
		// dispatcher another:
		this.dispatcher0.addHandler(Path.valueOf("gchat"), this.dispatcher1);

		// strict mode
		this.dispatcher1.addHandler(Path.valueOf("gchat", "join"), true,
				new JoinGMH(this));
		// strict mode
		this.dispatcher1.addHandler(Path.valueOf("gchat", "message"), true,
				new MessageGMH(this));

	}

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

	protected void onEndpointAuth(EndpointBondEvent t) {
		this.setConnected(true);
	}

	/**
	 * Dec 23, 2012
	 */
	protected void onEndpointOpen(EndpointOpenEvent t) {

	}

	protected void onEndpointClose(EndpointCloseEvent t) {
		// TODO access server for auto establish some thing.
		// TODO show view the connection to server is broke.
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void join(String gid) {
		GChatModel m = this.getModel();
		m.setGroupIdToJoin(gid);
		this.join();
	}

	@Override
	public void join() {
		//
		GChatModel m = this.getModel();
		String gid = m.getGroupIdToJoin(true);
		ChatGroupModel gm = this.getOrCreateGroup(gid);
		MessageData req = new MessageData();
		req.setHeader("path", "/gchat/join");
		req.setHeader("groupId", gid);
		this.endpoint.sendMessage(req);
	}
	
	public ChatGroupModel getOrCreateGroup(String gid){
		GChatModel m = this.getModel();
		ChatGroupModel gm = m.getGroup(gid, false);
		if (gm == null) {//
			gm = m.createGroup(gid);
			new GChatGroupCreatedEvent(this,gid).dispatch();
		}
		return gm;
	}

	public static LazyMvcI createLazyMvc(ModelI parent, String name) {
		LazyMvcI rt = new LazyMvcSupport(parent, name) {

			@Override
			protected ModelI createModel(String name) {
				//
				return new GChatModel(name);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new GChatView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new GChatControlImpl(name);
			}
		};
		return rt;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public GChatModel getChatModel() {
		//
		return (GChatModel) this.model;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	protected void doModel(ModelI cm) {
		super.doModel(cm);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void processChildModelAdd(ModelI parent, ModelI cm) {
		super.processChildModelAdd(parent, cm);
		if (cm instanceof ChatGroupModel) {
			this.onChildChatGroupModelAdd((ChatGroupModel) cm);
		}
	}

	/**
	 * Dec 23, 2012
	 */
	private void onChildChatGroupModelAdd(ChatGroupModel cm) {
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
		GChatModel m = this.getChatModel();
		m.setCurrentGroupId(gid);
		ChatGroupModel gm = this.getChatModel().getGroup(gid, true);
		gm.setMessageToSend(text);
		this.send();
	}

	@Override
	public void send() {
		String cid = this.getChatModel().getCurrentGroupId();
		if (cid == null) {
			throw new UiException(
					"please setCurrentGroupId() before send message");
		}
		ChatGroupModel gm = this.getChatModel().getGroup(cid, true);
		String text = gm.getMessageToSend();
		if (text == null) {
			throw new UiException("message to send is null");
		}
		MessageData req = new MessageData();
		req.setHeader("path", "/gchat/message");
		req.setHeader("groupId", cid);
		req.setHeader("format", "message");//
		MessageData msg = new MessageData("plain-text");
		msg.setHeader("format", "text");
		msg.setPayload("text", StringData.valueOf(text));
		req.setPayload("message", msg);
		this.endpoint.sendMessage(req);
	}

}
