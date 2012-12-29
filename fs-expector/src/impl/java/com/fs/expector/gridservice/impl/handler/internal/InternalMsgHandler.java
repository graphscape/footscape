/**
 * Aug 1, 2012
 */
package com.fs.expector.gridservice.impl.handler.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.internal.InternalMsgEW;

/**
 * @author wu<br>
 *         Signon with username/password,got a session id.
 */
public class InternalMsgHandler extends ExpectorTMREHSupport {

	private static final Logger LOG = LoggerFactory.getLogger(InternalMsgHandler.class);

	@Handle("after-session-binding")
	// internal event
	public void handleAnonymous(InternalMsgEW ew, HandleContextI hc, RequestI req, ResponseI res) {
		String sid = (String) ew.getMessage().getPayload("sessionId", true);
		SessionGd s = this.sessionManager.getSession(sid);

		Session rt = new Session().forCreate(this.dataService);
		rt.setAccountId(s.getAccountId());
		rt.setId(s.getId());
		rt.setClientId(s.getClientId());//
		rt.setProperty(Session.PK_IS_ANONYMOUS, s.getProperty("isAnonymous", true));
		rt.save(true);//

	}

}
