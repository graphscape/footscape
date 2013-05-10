/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.api.session;

import com.fs.commons.api.context.ContextI;

/**
 * @author wu
 * 
 */
public interface SessionI extends ContextI {

	public String getGroup();

	public String getId();

	public long getIdleTimeoutMs();

	public void destroy();

	public long getCreated();

	public long getTouched();

	public void touch();

	public boolean isTimeout();
	
}
