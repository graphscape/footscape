/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.impl.session;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.SessionManagerI;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;

/**
 * @author wuzhen
 * 
 */
public class SessionManagerImpl extends EntityGdManagerSupport<SessionGd>
		implements SessionManagerI {

	public SessionManagerImpl() {
		super("session", SessionGd.class);
	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public String createSession(SessionGd s) {
		//
		this.addEntity(s);
		
		return s.getId();

	}

	/*
	 * Dec 16, 2012
	 */
	@Override
	public SessionGd getSession(String id) {
		return this.getEntity(id);
	}

}
