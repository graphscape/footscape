/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package com.fs.uicore.impl.gwt.client.comet.ws;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.html5.CloseEventJSO;
import com.fs.uicore.api.gwt.client.html5.ErrorJSO;
import com.fs.uicore.api.gwt.client.html5.EventJSO;
import com.fs.uicore.api.gwt.client.html5.WebSocketJSO;
import com.fs.uicore.impl.gwt.client.comet.GometI;
import com.fs.uicore.impl.gwt.client.comet.GometSupport;

/**
 * @author wu
 * 
 */
public class WsGomet extends GometSupport {

	private WebSocketJSO socket;

	/**
	 * @param wso
	 */
	public WsGomet(WebSocketJSO wso) {
		super("websocket");
		this.socket = wso;
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open() {

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		this.socket.close();
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void send(String jsS) {
		this.socket.send(jsS);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onOpen(final HandlerI<GometI> handler) {
		this.socket.onOpen(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				handler.handle(WsGomet.this);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onClose(final HandlerI<String> handler) {
		//
		this.socket.onClose(new HandlerI<CloseEventJSO>() {

			@Override
			public void handle(CloseEventJSO t) {
				String msg = t.getCode() + "," + t.getReason();
				handler.handle(msg);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onError(final HandlerI<String> handler) {
		//
		this.socket.onError(new HandlerI<ErrorJSO>() {

			@Override
			public void handle(ErrorJSO t) {
				String msg = "" + t.getData();
				handler.handle(msg);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void onMessage(final HandlerI<String> handler) {
		this.socket.onMessage(new HandlerI<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				String msg = t.getData();

				handler.handle(msg);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.socket.isOpen();
	}

}
