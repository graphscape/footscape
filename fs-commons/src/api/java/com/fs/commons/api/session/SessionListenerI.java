/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.api.session;

/**
 * @author wu
 * 
 */
public interface SessionListenerI {

	public void onCreated(SessionI session);

	public void onTouched(SessionI session);

	public void onTimeout(SessionI session);

}
