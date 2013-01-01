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
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.event.EndpointCloseEvent;
import com.fs.uicore.api.gwt.client.event.EndpointErrorEvent;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;
import com.fs.uicore.api.gwt.client.event.EndpointOpenEvent;
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

	private String sessionId;

	private boolean serverIsReady;

	private String clientId;

	private String terminalId;

	private boolean bond;

	private MessageDispatcherI dispatcher;

	public EndpointWsImpl() {
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		UiClientI client = this.getClient(true);// .addh

		// message dispatcher
		MessageDispatcherI.FactoryI df = client.find(MessageDispatcherI.FactoryI.class, true);
		this.dispatcher = df.get(D_NAME);// for end point
		this.dispatcher.addHandler(Path.valueOf("/control/status/serverIsReady", '/'), new MessageHandlerI() {

			@Override
			public void handle(MessageData t) {
				EndpointWsImpl.this.onServerIsReady(t);
			}
		});
		MessageHandlerI bindingMH = new MessageHandlerI() {

			@Override
			public void handle(MessageData t) {
				EndpointWsImpl.this.onBindingSuccess(t);
			}
		};
		this.dispatcher.addHandler(Path.valueOf("/terminal/auth/success"), bindingMH);
		this.dispatcher.addHandler(Path.valueOf("/terminal/binding/success"), bindingMH);

	}

	protected void onServerIsReady(MessageData md) {
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
		if (this.sessionId != null) {
			req.setHeader("sessionId", this.sessionId);//
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
		// dispatch to #0 dispatcher.
		this.dispatcher.handle(md);// handler
		// and raise a event
		new EndpointMessageEvent(this, md).dispatch();

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
		return this.bond;
	}

	/*
	 * Dec 23, 2012
	 */
	@Override
	public String getSessionId() {
		//
		return this.sessionId;
	}

	/**
	 * Dec 23, 2012
	 */
	public void onBindingSuccess(MessageData md) {
		System.out.println("onBindingSuccess:" + md);
		this.sessionId = md.getString("sessionId", true);
		new EndpointBondEvent(this, this.sessionId).dispatch();
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public void sendMessage(MsgWrapper req) {
		this.sendMessage(req.getTarget());//
	}

}
