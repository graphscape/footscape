/**
 *  
 */
package com.fs.uiclient.api.gwt.client.facebook;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

/**
 * @author wu
 * 
 */
public class FacebookJsSdk {

	private String jsSrcElementId = "facebook-jssdk";

	public FacebookJsSdk() {

	}

	public void init() {
		Document doc = Document.get();
		Element fbJs = doc.getElementById(this.jsSrcElementId);
		if (fbJs != null) {
			return;
		}
		//
		
		// add the script element
		fbJs = doc.createScriptElement();
		fbJs.setId(this.jsSrcElementId);
		fbJs.setAttribute("src", "//connect.facebook.net/en_US/all.js");
		doc.getBody().insertFirst(fbJs);

	}

	public native void setFbAsyncInitFunction(String appId_)/*-{
		$wnd.fbAsyncInit = function() {
        FB.init({
          appId      : appId_, // App ID
          channelUrl : '/channel.html', // Channel File
          status     : true, // check login status
          cookie     : true, // enable cookies to allow the server to access the session
          xfbml      : true // parse XFBML
        });
	
	}-*/;

}
