/**
 *  
 */
package com.fs.uiclient.api.gwt.client.facebook;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.support.ErrorReportProxyHandler;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class Facebook {

	private static String jsSrcElementId = "facebook-jssdk";

	private static Facebook ME;
	
	private static String appId = "123";//TODO
	
	//private Map<String,HandlerI<JavaScriptObject>> hdlMap;
	
	public Facebook() {
		//this.hdlMap = new HashMap<String,HandlerI<JavaScriptObject>> ();
	}

	public static Facebook getInstance() {
		if(ME != null){
			return ME;
		}
		Facebook rt = new Facebook();
		ME = rt;
		return rt;

	}
	
	public void onAuthLogin(final HandlerI<AuthLoginResponseJso> handler) {

		this.onEvent("auth.login", new HandlerI<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				AuthLoginResponseJso jso = t.cast();
				handler.handle(jso);
			}

		});

	}
	
	public void onEvent(String event, HandlerI<JavaScriptObject> handler) {
		handler = new ErrorReportProxyHandler<JavaScriptObject>(handler) ;		
		//this.hdlMap.put(event, handler);//
		this.onEventInternal(event, new ErrorReportProxyHandler<JavaScriptObject>(handler));
	}

	private native void onEventInternal(String event, HandlerI<JavaScriptObject> handler)
	/*-{
		var func = function (evt){
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(evt);
		};
		FB.Event.subscribe(event,func);
	}-*/;
	
	public void start(boolean reload) {
		
		//TODO reload?
		
		Document doc = Document.get();
		Element fbJs = doc.getElementById(jsSrcElementId);
		if (fbJs != null) {
			return;
		}
		//prepare the init function
		this.setFbAsyncInitFunction(appId);
		// add the script element
		fbJs = doc.createScriptElement();
		fbJs.setId(jsSrcElementId);
		fbJs.setAttribute("src", "//connect.facebook.net/en_US/all.js");
		doc.getBody().insertFirst(fbJs);

	}

	public static Element createLoginButtonDiv() {
		Element rt = DOM.createDiv();
		rt.addClassName("fb-login-button");
		rt.setAttribute("data-scope", "user_likes,user_photos");

		return rt;
	}
	
//	private void onEvent(String evt, JavaScriptObject data){
//		HandlerI<JavaScriptObject> hdl = hdlMap.get(evt);
//		if(hdl == null){
//			return;
//		}
//		hdl.handle(data);
//	}
	
	public void login(final HandlerI<AuthLoginResponseJso> handler){
		this.onAuthLogin(handler);
		
	}
	private native void getLoginStatus(HandlerI<JavaScriptObject> handler)
	/*-{
		var func = function(response){
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};
		FB.getLoginStatus(func);
		
	}-*/;
	private native void doLogin(HandlerI<AuthLoginResponseJso> handler)
	/*-{
		var func = function(response){
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};
		FB.login(func);
		
	}-*/;
	
	private native JavaScriptObject responseFunction(JavaScriptObject response)/*-{
		return function(response){
			handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(response);
		};
	}-*/;
	
	private native void setFbAsyncInitFunction(String appId_)
	/*-{

		$wnd.fbAsyncInit = function() {
			FB.init({
				appId : appId_, // App ID
				channelUrl : '/channel.html', // Channel File
				status : true, // check login status
				cookie : true, // enable cookies to allow the server to access the session
				xfbml : true
			// parse XFBML
			});
			
		};
	}-*/;

	/**
	 *
			var func = function (evt){
				this.@com.fs.uiclient.api.gwt.client.facebook::onEvent(Ljava.lang.String;Ljava/lang/Object;)('auth.login',evt);
			};
			FB.Event.subscribe('auth.login',func); 
			
	 */
}
