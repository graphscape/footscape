/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.impl.gwt.client.gchat;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.GChatModel;
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
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class GChatControlImpl extends ControlSupport implements GChatControlI {

	protected EndPointI endpoint;

	protected MessageDispatcherI dispatcher0;

	protected MessageDispatcherI dispatcher1;

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
		MessageDispatcherI.FactoryI df = this.getClient(true).getChild(MessageDispatcherI.FactoryI.class,
				true);
		this.dispatcher0 = df.get(0);//
		this.dispatcher1 = df.get(1);// for gchat
		this.dispatcher0.addHandler(Path.valueOf("/gchat"), this.dispatcher1);

		this.dispatcher1.addHandler(Path.valueOf("/gchat/join"), new JoinGMH(this));
		this.dispatcher1.addHandler(Path.valueOf("/gchat/message"), new MessageGMH(this));

	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public void join() {
		//
		GChatModel m = this.getModel();
		String gid = m.getGroupIdToJoin(true);
		ChatGroupModel gm = m.createGroup(gid);
		MessageData req = new MessageData();
		req.setHeader("path", "/gchat/join");
		req.setHeader("groupId", gid);
		this.endpoint.sendMessage(req);
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

}
