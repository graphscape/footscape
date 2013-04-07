/**
 *  
 */
package com.fs.uicommons.api.gwt.client.facebook;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class Facebook {

	private static String jsSrcElementId = "facebook-jssdk";

	private static FBJSO FB;

	private static String appId = "120488868142584";// TODO

	private static String appSecret = "f113280c5ab50afe4f3b5a35e21ba9f4";

	private static List<HandlerI<FBJSO>> readyHandlers = new ArrayList<HandlerI<FBJSO>>();

	public static void getInstance(HandlerI<FBJSO> readyH) {
		if (FB != null) {
			readyH.handle(FB);
		}

		readyHandlers.add(readyH);

		Document doc = Document.get();
		com.google.gwt.dom.client.Element domE = doc.getElementById(jsSrcElementId);
		if (domE == null) {
			
			//
			// add root element
			Element root = DOM.createDiv();
			root.setId("fb-root");
			doc.getBody().insertFirst(root);
			// prepare the init function
			setFbAsyncInitFunction(appId);
			// add the script element
			Element fbJs = doc.createScriptElement().cast();
			fbJs.setId(jsSrcElementId);
			fbJs.setAttribute("src", "https://connect.facebook.net/en_US/all.js");
			doc.getBody().insertAfter(fbJs, root);
		}

	}

	public static native void setFbAsyncInitFunction(String appId_)
	/*-{

		$wnd.fbAsyncInit = function() {
			//alert('before fb init:');
			var pts = {
				appId : appId_, // App ID
				channelUrl : '//localhost:8888/facebook/channel.html', // Channel File
				status : true, // check login status
				cookie : true, // enable cookies to allow the server to access the session
				xfbml : true
			// parse XFBML
			};
			//alert('appId:' + pts.appId);

			if (typeof ($wnd.FB) == 'undefined' || $wnd.FB == null) {
				alert('$wnd.FB not defined.');
			}

			$wnd.FB.init(pts);
			//alert('after fb init');
			@com.fs.uicommons.api.gwt.client.facebook.Facebook::afterFBInit(Ljava/lang/Object;)($wnd.FB);
		};
	}-*/;

	private static void afterFBInit(Object fbJso) {
		JavaScriptObject jso = (JavaScriptObject)fbJso;
		FBJSO fb = jso.cast();
		FB = fb;
		for (HandlerI<FBJSO> h : readyHandlers) {
			h.handle(fb);//
		}
	}

	public static Element createLoginButtonDiv() {
		Element rt = DOM.createDiv();
		rt.addClassName("fb-login-button");
		rt.setAttribute("data-scope", "user_likes,user_photos");
		return rt;
	}

	private static native boolean isFBDefined()
	/*-{
		if (typeof ($wnd.FB) != 'undefined' && $wnd.FB != null) {
			return true;
		} else {
			return false;
		}
	}-*/;

}
