/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.api.session;

import com.fs.commons.api.service.HandlerI;

/**
 * @author wu
 * 
 */
public interface SessionManagerI {

	public String getName();

	public SessionI getSession(String id);

	public SessionI touchSession(String id);

	public SessionI createSession(long timeout);

	public SessionI createSession(String id, long timeout);

	public void addCreatedHandler(HandlerI<SessionI> hdl);

	public void addTimeoutHandler(HandlerI<SessionI> hdl);

	public void addTouchedHandler(HandlerI<SessionI> hdl);

}
