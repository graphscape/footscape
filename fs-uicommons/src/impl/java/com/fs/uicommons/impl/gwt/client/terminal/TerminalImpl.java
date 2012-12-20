/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.terminal;

import com.fs.uicommons.api.gwt.client.html5.websocket.WebSocketJSO;
import com.fs.uicommons.api.gwt.client.terminal.TerminalI;
import com.fs.uicommons.api.gwt.client.terminal.event.MessageEvent;
import com.fs.uicore.api.gwt.client.CodecI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class TerminalImpl extends UiObjectSupport implements TerminalI {

	private WebSocketJSO socket;

	private CodecI messageCodec;

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		this.messageCodec = this.getClient(true).getCodecFactory().getCodec(MessageData.class);

		String url = (String) this.getClient(true).getProperty(UiClientI.ROOT_URi);
		String uri = "ws://localhost:8080/wsa/default";
		this.socket = WebSocketJSO.newInstance(uri);
		this.socket.onMessage(new UiCallbackI<String, Object>() {

			@Override
			public Object execute(String t) {
				//
				TerminalImpl.this.onMessage(t);
				return null;
			}
		});
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void sendMessage(MessageData req) {
		//
		JSONValue js = (JSONValue) this.messageCodec.encode(req);
		String jsS = js.toString();
		this.socket.send(jsS);
	}

	protected void onMessage(String jsonS) {
		JSONValue jsonV = JSONParser.parseStrict(jsonS);
		MessageData md = (MessageData) this.messageCodec.decode(jsonV);
		new MessageEvent(this, md).dispatch();
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	public void addMessageHandler(String path, HandlerI<MessageEvent> hdl) {
		//
		this.addHandler(new MessageEventFilter(path), hdl);
	}

}
