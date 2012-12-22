/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.channel;

import com.fs.uicommons.api.gwt.client.channel.ChannelI;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelCloseEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelErrorEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelMessageEvent;
import com.fs.uicommons.api.gwt.client.channel.event.ChannelOpenEvent;
import com.fs.uicommons.api.gwt.client.html5.websocket.WebSocketJSO;
import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
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
public class ChannelImpl extends UiObjectSupport implements ChannelI {

	private WebSocketJSO socket;

	private CodecI messageCodec;

	private String uri;

	public ChannelImpl() {

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

		UiClientI client = this.getClient(true);// .addh

		client.addHandler(AfterClientStartEvent.TYPE, new HandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent e) {
				// terminai
				ChannelImpl.this.onClientStart(e);
			}
		});
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
				ChannelImpl.this.onOpen(t);
				return null;
			}
		});
		this.socket.onMessage(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				ChannelImpl.this.onMessage(t);
				return null;
			}
		});
		this.socket.onClose(new UiCallbackI<Object, Object>() {

			@Override
			public Object execute(Object t) {
				//
				ChannelImpl.this.onClose(t);
				return null;
			}
		});
		this.socket.onError(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				ChannelImpl.this.onError(t);
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
		new ChannelOpenEvent(this).dispatch();
	}

	protected void onClose(Object evt) {
		new ChannelCloseEvent(this).dispatch();

	}

	protected void onError(String jsonS) {
		new ChannelErrorEvent(this, jsonS).dispatch();

	}

	protected void onMessage(String jsonS) {
		JSONValue jsonV = JSONParser.parseStrict(jsonS);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		new ChannelMessageEvent(this, md).dispatch();
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void addMessageHandler(String path, HandlerI<ChannelMessageEvent> hdl) {
		//
		this.addHandler(new MessageEventFilter(path), hdl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.channel.ChannelI#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.socket.isOpen();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.channel.ChannelI#getUri()
	 */
	@Override
	public String getUri() {
		return this.uri;
	}

}
