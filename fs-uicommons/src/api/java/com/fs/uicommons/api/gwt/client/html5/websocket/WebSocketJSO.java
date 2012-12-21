/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.html5.websocket;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wu
 *         <p>
 *         http://dev.w3.org/html5/websockets/#the-websocket-interface
 */
public final class WebSocketJSO extends JavaScriptObject {

	public static final short CONNECTING = 0;
	public static final short OPEN = 1;
	public static final short CLOSING = 2;
	public static final short CLOSED = 3;

	protected WebSocketJSO() {

	}

	public static native WebSocketJSO newInstance(String uri)
	/*-{
		var  rt = new WebSocket(uri); 		 
		return rt;
	}-*/;

	public native void onOpen(UiCallbackI<Object, Object> handler)
	/*-{
		this.onopen = function (evt){
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/Object;)(evt);
		}
	}-*/;

	public native void onClose(UiCallbackI<Object, Object> handler)
	/*-{
		this.onclose = function (evt){
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/Object;)(evt);
		}
	}-*/;

	public native void onMessage(UiCallbackI<String, Object> handler)
	/*-{
		this.onmessage = function (evt){
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/String;)(evt.data);
		}
	}-*/;

	public native void onError(UiCallbackI<String, Object> handler)
	/*-{
		this.onerror = function (evt){
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/String;)(evt.data);
		}
	}-*/;

	public native void send(String msg)
	/*-{
										this.send(msg);
										}-*/;

	public native short getReadyState()
	/*-{
		return this.readyState;
	}-*/;

	/**
	 * @return
	 */
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return this.getReadyState() == OPEN;
	}
}
