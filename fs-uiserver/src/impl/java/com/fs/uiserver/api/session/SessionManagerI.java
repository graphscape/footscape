/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 9, 2012
 */
package com.fs.uiserver.api.session;

/**
 * @author wu
 * @see RequestI.getProperty();
 */
public interface SessionManagerI {

	public SessionContext getSessionContext(String sid, boolean force);

	public SessionContext createSession();
	
}
