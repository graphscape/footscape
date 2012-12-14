/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.session;

import com.fs.expector.api.session.SessionI;
import com.fs.expector.api.session.SessionManagerI;
import com.fs.expector.impl.dg.support.DgMapManagerImplSupport;

/**
 * @author wuzhen
 * 
 */
public class SessionManagerImpl extends
		DgMapManagerImplSupport<SessionI, SessionImpl> implements
		SessionManagerI {

	public SessionManagerImpl() {
		super(SessionI.class, SessionImpl.class);
	}

}
