/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.session;

import com.fs.expector.api.session.SessionI;
import com.fs.expector.impl.dg.support.MemberPropertiesDWSupport;

/**
 * @author wuzhen
 * 
 */
public class SessionImpl extends MemberPropertiesDWSupport implements SessionI {

	// for get from DgMapI.
	public SessionImpl() {

	}

	// for create new one.
	public SessionImpl(String id) {
		super(id);
	}

}
