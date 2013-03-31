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
public final class AuthResponseJSO extends JavaScriptObject {

	protected AuthResponseJSO() {

	}

	public native String getAccessToken()
	/*-{
		return this.accessToken;
	}-*/;

	public native String getUserID()
	/*-{
		return this.userID;
	}-*/;

}
