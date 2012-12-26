/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicommons.api.gwt.client.html5.websocket;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 *         <p>
 *         http://dev.w3.org/html5/websockets/#the-websocket-interface
 */
public final class WebSocketJSO extends JavaScriptObject {
	//
	// public static final short CONNECTING = 0;
	// public static final short OPEN = 1;
	// public static final short CLOSING = 2;
	// public static final short CLOSED = 3;

	protected WebSocketJSO() {

	}

	public static WebSocketJSO newInstance(String uri, boolean force) {

		WebSocketJSO rt = tryCreate(uri);
		if (force && rt == null) {
			String agent = Window.Navigator.getUserAgent();
			throw new UiException("browser not support web socket,agent:" + agent);
		}
		return rt;
	}

	private static native WebSocketJSO tryCreate(String uri)
	/*-{
	    if($wnd.WebSocket){
			var  rt = new WebSocket(uri); 		 
			return rt;
		}
		if($wnd.MozWebSocket){
			var  rt = new MozWebSocket(uri); 		 
			return rt;
		}
		return null;
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
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/Object;)(evt.data);
		}
	}-*/;

	public native void onError(UiCallbackI<String, Object> handler)
	/*-{
		this.onerror = function (evt){
			handler.@com.fs.uicore.api.gwt.client.core.UiCallbackI::execute(Ljava/lang/Object;)(evt.data);
		}
	}-*/;

	public native void send(String msg)
	/*-{
										this.send(msg);
										}-*/;

	public native short getReadyState()
	/*-{
		var rt = this.readyState;
		//FOR:Caused by: com.google.gwt.dev.shell.HostedModeException: 
		//Something other than a short was returned from JSNI method 
		//'@com.fs.uicommons.api.gwt.client.html5.websocket.WebSocketJSO::getReadyState()': 
		//JS value of type undefined, expected short
		if(rt == undefined){
			rt = -1;
		}
		return rt;
	}-*/;

	/**
	 * @return
	 */
	public final boolean isOpen() {
		// TODO Auto-generated method stub
		return this.getReadyState() == 1;
	}
}