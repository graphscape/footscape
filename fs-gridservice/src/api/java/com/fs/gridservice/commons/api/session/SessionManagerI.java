/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.session;

import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.data.SessionGd;

/**
 * @author wu
 * 
 */
public interface SessionManagerI extends EntityGdManagerI<SessionGd> {

	public String createSession(SessionGd gd);

	public SessionGd getSession(String id);

}
