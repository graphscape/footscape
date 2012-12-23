/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.endpoint;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointCloseEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointErrorEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointMessageEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointOpenEvent;
import com.fs.uicommons.api.gwt.client.html5.websocket.WebSocketJSO;
import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
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

	private MessageDispatcherI dispatcher0;

	public EndpointWsImpl() {

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

		UiClientI client = this.getClient(true);// .addh

		client.addHandler(AfterClientStartEvent.TYPE, new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent e) {
				// terminai
				EndpointWsImpl.this.onClientStart(e);
			}
		});
		// message dispatcher
		MessageDispatcherI.FactoryI df = client.find(MessageDispatcherI.FactoryI.class, true);
		this.dispatcher0 = df.get(0);// for end point

	}

	protected void onClientStart(AfterClientStartEvent e) {
		UiClientI client = (UiClientI) e.getSource();

		String sid = client.getSessionId();

		String host = Window.Location.getHostName();
		String port = Window.Location.getPort();
		port = "8080";// for testing.
		this.uri = "ws://" + host + ":" + port + "/wsa/default";

		this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);

		String url = (String) this.getClient(true).getProperty(UiClientI.ROOT_URi);
		this.socket = WebSocketJSO.newInstance(uri, true);
		this.socket.onOpen(new UiCallbackI<Object, Object>() {

			@Override
			public Object execute(Object t) {
				//
				EndpointWsImpl.this.onOpen(t);
				return null;
			}
		});
		this.socket.onMessage(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				EndpointWsImpl.this.onMessage(t);
				return null;
			}
		});
		this.socket.onClose(new UiCallbackI<Object, Object>() {

			@Override
			public Object execute(Object t) {
				//
				EndpointWsImpl.this.onClose(t);
				return null;
			}
		});
		this.socket.onError(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				EndpointWsImpl.this.onError(t);
				return null;
			}
		});

	}

	protected void assertSocketOpen() {
		if (this.socket != null && this.socket.isOpen()) {
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
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.socket.send(jsS);
	}

	protected void onOpen(Object evt) {
		new EndpointOpenEvent(this).dispatch();
	}

	protected void onClose(Object evt) {
		new EndpointCloseEvent(this).dispatch();

	}

	protected void onError(String jsonS) {
		new EndpointErrorEvent(this, jsonS).dispatch();

	}

	protected void onMessage(String jsonS) {
		JSONValue jsonV = JSONParser.parseStrict(jsonS);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		// dispatch to #0 dispatcher.
		this.dispatcher0.handle(md);// handler
		// and event
		new EndpointMessageEvent(this, md).dispatch();

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void addMessageHandler(String path, EventHandlerI<EndpointMessageEvent> hdl) {
		//
		this.addHandler(new MessageEventFilter(path), hdl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.endpoint.EndPointI#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.socket.isOpen();

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

}
