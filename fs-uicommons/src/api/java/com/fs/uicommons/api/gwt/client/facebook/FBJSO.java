/**
 *  
 */
package com.fs.uicommons.api.gwt.client.facebook;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wu
 * 
 */
public final class FBJSO extends JavaScriptObject {

	// private Map<String,HandlerI<JavaScriptObject>> hdlMap;

	protected FBJSO() {

	}

	public native void login(final HandlerI<LoginResponseJSO> handler)
	/*-{
		var func = function(response) {
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};

		this.login(func);
	}-*/;

	public native void getLoginStatus(HandlerI<LoginStatusResponseJSO> handler)
	/*-{
		var func = function(response) {
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};
		this.getLoginStatus(func);

	}-*/;

	public void me(final HandlerI<MeResponseJSO> h) {
		this.api("/me", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				h.handle((MeResponseJSO) t.cast());
			}
		});
	}

	public native void api(String api, HandlerI<JavaScriptObject> handler)
	/*-{
		var func = function(response) {
			//alert('after response:'+response);
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};
		//alert('before api:'+api);
		this.api(api, func);
	}-*/;

}
