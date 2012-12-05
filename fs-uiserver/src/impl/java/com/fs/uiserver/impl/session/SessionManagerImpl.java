/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 9, 2012
 */
package com.fs.uiserver.impl.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.FsException;
import com.fs.uiserver.api.session.SessionContext;
import com.fs.uiserver.api.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class SessionManagerImpl extends ConfigurableSupport implements
		SessionManagerI {

	private Map<String, SessionContext> sessionContextMap;

	public SessionManagerImpl() {
		this.sessionContextMap = new HashMap<String, SessionContext>();
	}

	@Override
	public SessionContext getSessionContext(String sid, boolean force) {
		SessionContext rt = this.sessionContextMap.get(sid);
		if (rt == null && force) {
			throw new FsException("force,session id:" + sid);
		}
		return rt;
	}

	// TODO timeout of session check.

	@Override
	public SessionContext createSession() {
		String id = UUID.randomUUID().toString();
		SessionContext rt = new SessionContext(id);
		this.sessionContextMap.put(id, rt);
		return rt;
	}

}
