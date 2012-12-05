/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 9, 2012
 */
package com.fs.uiserver.api.session;

import com.fs.commons.api.context.support.ContextSupport;

/**
 * @author wu
 * 
 */
public class SessionContext extends ContextSupport {

	private String id;

	public SessionContext(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
