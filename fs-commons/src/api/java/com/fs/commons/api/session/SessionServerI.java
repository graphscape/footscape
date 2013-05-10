/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.api.session;

import com.fs.commons.api.server.ServerI;

/**
 * @author wu
 * 
 */
public interface SessionServerI extends ServerI {

	public SessionManagerI createManager(String name);

	public SessionManagerI getManager(String name);

}
