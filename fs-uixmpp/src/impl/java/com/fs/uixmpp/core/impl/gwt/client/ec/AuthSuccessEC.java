/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 18, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client.ec;

import tigase.jaxmpp.core.client.xmpp.modules.auth.AuthModule;
import tigase.jaxmpp.core.client.xmpp.modules.auth.AuthModule.AuthEvent;

import com.fs.uixmpp.core.api.gwt.client.event.AuthSuccessEvent;
import com.fs.uixmpp.core.impl.gwt.client.EventConverter;

/**
 * @author wu
 * 
 */
public class AuthSuccessEC extends EventConverter<AuthEvent, AuthSuccessEvent> {

	/**
	 * @param et
	 */
	public AuthSuccessEC() {
		super(AuthModule.AuthSuccess, "AuthSuccess");
	}

	@Override
	public AuthSuccessEvent convert(Context<AuthEvent> e) {

		AuthSuccessEvent rt = new AuthSuccessEvent(e.getXmpp());
		return rt;
	}

}
