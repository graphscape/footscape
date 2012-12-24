/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.endpoint;

import com.fs.uicommons.api.gwt.client.endpoint.EndPointI;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointBondEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointCloseEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointErrorEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointMessageEvent;
import com.fs.uicommons.api.gwt.client.endpoint.event.EndpointOpenEvent;
import com.fs.uicommons.api.gwt.client.frwk.login.event.AfterAuthEvent;
import com.fs.uicommons.api.gwt.client.html5.websocket.WebSocketJSO;
import com.fs.uicommons.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
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

	private boolean bond;

	private MessageDispatcherI dispatcher0;

	public EndpointWsImpl() {

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		this.getEventBus(true).addHandler(AfterAuthEvent.TYPE,
				new EventHandlerI<AfterAuthEvent>() {

					@Override
					public void handle(AfterAuthEvent t) {
						EndpointWsImpl.this.afterAuth(t);
					}
				});
		UiClientI client = this.getClient(true);// .addh

		// message dispatcher
		MessageDispatcherI.FactoryI df = client.find(
				MessageDispatcherI.FactoryI.class, true);
		this.dispatcher0 = df.get(0);// for end point
		this.dispatcher0.addHandler(
				Path.valueOf("/control/status/serverIsReady", '/'),
				new ServerIsReadyMH(this));

		this.dispatcher0.addHandler(
				Path.valueOf("/terminal/binding/success", '/'),
				new BIndingSuccessMH(this));
	}

	/**
	 * Dec 23, 2012
	 */
	protected void afterAuth(AfterAuthEvent t) {
		this.sessionId = t.getSessionId();// session id is got from
											// LoginControlI.
		// now start connect and bond session with channel/terminal
		if (Boolean.valueOf(this.getClient(true).getParameter(
				CP_WEBSOCKET_DISABLE, "false"))) {
			logger.info("websocket disabled by client parameter:"
					+ CP_WEBSOCKET_DISABLE);
			return;
		}
		this.start();
	}

	protected void start() {
		UiClientI client = this.getClient(true);//

		String clientId = client.getClientId();//
		String host = Window.Location.getHostName();
		String port = Window.Location.getPort();
		port = "8080";// for testing.
		this.uri = "ws://" + host + ":" + port + "/wsa/default";

		this.messageCodec = this.getClient(true).getCodecFactory()
				.getCodec(MessageData.class);

		String url = (String) this.getClient(true).getProperty(
				UiClientI.ROOT_URi);
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
		if (this.sessionId != null) {
			req.setHeader("sessionId", this.sessionId);//
		}
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
		// and raise a event
		new EndpointMessageEvent(this, md).dispatch();

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void addMessageHandler(String path,
			EventHandlerI<EndpointMessageEvent> hdl) {
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
	public void bindingSuccess() {
		this.bond = true;
		new EndpointBondEvent(this, this.sessionId).dispatch();
	}

}
