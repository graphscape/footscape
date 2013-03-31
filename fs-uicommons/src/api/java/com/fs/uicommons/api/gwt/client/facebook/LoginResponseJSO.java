/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 26, 2013
 */
package com.fs.uicommons.api.gwt.client.facebook;

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
public final class LoginResponseJSO extends JavaScriptObject {

	protected LoginResponseJSO() {
	}

	public native AuthResponseJSO getAuthResponse()
	/*-{
		return this.authResponse;
	}-*/;
}
