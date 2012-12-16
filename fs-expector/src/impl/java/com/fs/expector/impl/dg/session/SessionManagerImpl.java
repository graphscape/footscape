/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.session;

import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.api.data.SessionGd;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.support.MapSupport;

/**
 * @author wuzhen
 * 
 */
public class SessionManagerImpl extends MapSupport<SessionGd> implements SessionManagerI {

	public SessionManagerImpl() {
		super(SessionGd.class);
	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public SessionGd createSession(PropertiesI<Object> pts) {
		//
		return null;
	}

}
