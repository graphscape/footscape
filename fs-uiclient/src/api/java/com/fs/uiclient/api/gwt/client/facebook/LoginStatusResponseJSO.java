/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 26, 2013
 */
package com.fs.uiclient.api.gwt.client.facebook;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wu <code>
 {
    status: 'connected',
    authResponse: {
        accessToken: '...',
        expiresIn:'...',
        signedRequest:'...',
        userID:'...'
    }
}

</code> ref:
 *         https://developers.facebook.com/docs/howtos/login/getting-started/
 */
public final class LoginStatusResponseJSO extends JavaScriptObject {
	public static final String connected = "connected";
	public static final String not_authorized = "not_authorized";
	public static final String not_logged_in = "not_logged_in";

	protected LoginStatusResponseJSO() {

	}

	public boolean isConnected() {
		return this.isStatus(connected);
	}

	public boolean isNotAuthorized() {
		return this.isStatus(not_authorized);
	}

	public boolean isNotLogin() {
		return this.isStatus(not_logged_in);
	}

	public boolean isStatus(String s) {
		return s.equals(this.getStatus());
	}

	public native String getStatus()
	/*-{
		return this.status;
	}-*/;
	
	public native AuthResponseJSO getAuthResponse()
	/*-{
		return this.authResponse;
	}-*/;

}
