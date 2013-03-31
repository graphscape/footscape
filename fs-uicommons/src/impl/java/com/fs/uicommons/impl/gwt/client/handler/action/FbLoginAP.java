/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uicommons.impl.gwt.client.handler.action;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.facebook.AuthResponseJSO;
import com.fs.uicommons.api.gwt.client.facebook.FBJSO;
import com.fs.uicommons.api.gwt.client.facebook.Facebook;
import com.fs.uicommons.api.gwt.client.facebook.LoginResponseJSO;
import com.fs.uicommons.api.gwt.client.facebook.MeResponseJSO;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;

/**
 * @author wu create a new exp
 */
public class FbLoginAP extends ActionHandlerSupport {

	FBJSO fbJso;

	/**
	 * @param c
	 */
	public FbLoginAP(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(ActionEvent ae) {
		Facebook.getInstance(new HandlerI<FBJSO>() {

			@Override
			public void handle(FBJSO t) {
				FbLoginAP.this.onFacebook(t);
			}
		});

	}

	/**
	 * @param t
	 */
	protected void onFacebook(FBJSO fb) {
		this.fbJso = fb;
		fb.login(new HandlerI<LoginResponseJSO>() {

			@Override
			public void handle(LoginResponseJSO t) {
				FbLoginAP.this.onLogin(t);
			}
		});

	}

	/**
	 * @param t
	 */
	protected void onLogin(LoginResponseJSO t) {
		AuthResponseJSO ar = t.getAuthResponse();
		if (ar == null) {
			// Window.alert("canceled");
			return;
		}
		final String uid = ar.getUserID();

		// Window.alert("uid:" + uid);
		this.fbJso.me(new HandlerI<MeResponseJSO>() {

			@Override
			public void handle(MeResponseJSO t) {
				FbLoginAP.this.onMe(uid, t);
			}
		});
	}

	protected void onMe(String uid, MeResponseJSO mr) {
		PropertiesData<Object> pts = new PropertiesData<Object>();
		String nick = mr.getName();
		pts.setProperty("type", "external");
		pts.setProperty("source", "facebook");//
		pts.setProperty("userId", uid);
		pts.setProperty("nick", nick);
		//TODO ?
		this.getEndpoint().auth(pts);
		
	}

}
