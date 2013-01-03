/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.impl.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
import com.fs.uicore.api.gwt.client.event.EndpointUnbondEvent;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointWsImpl extends UiObjectSupport implements EndPointI {

	private WebSocketJSO socket;

	private CodecI messageCodec;

	private String uri;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private MessageDispatcherI dispatcher;

	private UserInfo userInfo;

	/**
	 * @param md
	 */
	public EndpointWsImpl(MessageDispatcherI md) {
		this.dispatcher = md;
		this.dispatcher.addHandler(Path.valueOf("/control/status/serverIsReady", '/'), new MessageHandlerI() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointWsImpl.this.onServerIsReady(t);
			}
		});
		MessageHandlerI bindingMH = new MessageHandlerI() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointWsImpl.this.onBindingSuccess(t);
			}
		};
		this.dispatcher.addHandler(Path.valueOf("/terminal/auth/success"), bindingMH);
		this.dispatcher.addHandler(Path.valueOf("/terminal/binding/success"), bindingMH);

		MessageHandlerI unBindingMH = new MessageHandlerI() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointWsImpl.this.onUnbindingSuccess(t);
			}
		};
		this.dispatcher.addHandler(Path.valueOf("/terminal/unbinding/success"), unBindingMH);

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	protected void onServerIsReady(EndpointMessageEvent e) {
		MessageData md = e.getMessage();
		this.clientId = md.getString("clientId", true);
		this.terminalId = md.getString("terminalId", true);
		this.serverIsReady = true;
		new EndpointOpenEvent(this).dispatch();
	}

	@Override
	public void open() {
		UiClientI client = this.getClient(true);//

		String host = Window.Location.getHostName();
		String port = Window.Location.getPort();
		port = "8080";// for testing.
		this.uri = "ws://" + host + ":" + port + "/wsa/default";

		this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);

		this.socket = WebSocketJSO.newInstance(uri, true);
		this.socket.onOpen(new UiCallbackI<Object, Object>() {

			@Override
			public Object execute(Object t) {
				//
				EndpointWsImpl.this.onWsOpen(t);
				return null;
			}
		});
		this.socket.onMessage(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				EndpointWsImpl.this.onWsMessage(t);
				return null;
			}
		});
		this.socket.onClose(new UiCallbackI<Object, Object>() {

			@Override
			public Object execute(Object t) {
				//
				EndpointWsImpl.this.onWsClose(t);
				return null;
			}
		});
		this.socket.onError(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				EndpointWsImpl.this.onWsError(t);
				return null;
			}
		});

	}

	protected void assertSocketOpen() {
		if (this.socket != null && this.socket.isOpen() && this.serverIsReady) {
			return;
		}

		throw new UiException("socket is not ready.");

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void sendMessage(MessageData req) {
		//
		this.assertSocketOpen();
		if (this.userInfo != null) {
			req.setHeader("sessionId", this.getSessionId());//
		}
		req.setHeader("_resonse_address", "tid://" + this.terminalId);
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.socket.send(jsS);
	}

	protected void onWsOpen(Object evt) {
		// wait server is ready
	}

	protected void onWsClose(Object evt) {
		this.serverIsReady = false;
		this.clientId = null;
		this.terminalId = null;//
		new EndpointCloseEvent(this).dispatch();

	}

	protected void onWsError(String jsonS) {
		new EndpointErrorEvent(this, jsonS).dispatch();

	}

	protected void onWsMessage(String jsonS) {
		JSONValue jsonV = JSONParser.parseStrict(jsonS);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		EndpointMessageEvent evt = new EndpointMessageEvent(this, md).dispatch();

		// dispatch to #0 dispatcher.
		this.dispatcher.handle(evt);// handler
		// and raise a event

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public MessageDispatcherI getMessageDispatcher() {
		//
		return this.dispatcher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.serverIsReady;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#getUri()
	 */
	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public void auth(PropertiesData<UiData> pts) {
		MessageData req = new MessageData("/terminal/auth");
		req.setPayloads(pts);
		this.sendMessage(req);
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public boolean isBond() {
		//
		return this.userInfo != null;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public String getSessionId() {
		//
		return this.userInfo.getString("sessionId", true);
	}

	/**
	 * Dec 23, 2012
	 */
	public void onBindingSuccess(EndpointMessageEvent evt) {
		MessageData md = evt.getMessage();
		System.out.println("onBindingSuccess:" + md);
		this.userInfo = new UserInfo();
		String sid = md.getString("sessionId", true);
		this.userInfo.setProperties(md.getPayloads());

		new EndpointBondEvent(this, this.getSessionId()).dispatch();
	}

	public void onUnbindingSuccess(EndpointMessageEvent evt) {
		this.userInfo = null;
		new EndpointUnbondEvent(this).dispatch();
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public void sendMessage(MsgWrapper req) {
		this.sendMessage(req.getTarget());//
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void logout() {
		//
		if (!this.isBond()) {
			throw new UiException("not bound yet.");
		}

		MessageData req = new MessageData("/terminal/unbinding");
		req.setPayload("sessionId", this.getSessionId());
		this.sendMessage(req);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public UserInfo getUserInfo() {
		//
		return this.userInfo;
	}

}
