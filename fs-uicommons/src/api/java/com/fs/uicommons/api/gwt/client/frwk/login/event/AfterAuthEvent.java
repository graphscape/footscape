/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.login.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class AfterAuthEvent extends Event {

	public static final Type<AfterAuthEvent> TYPE = new Type<AfterAuthEvent>("unknow");

	protected String sessionId;
	
	/**
	 * @param type
	 */
	public AfterAuthEvent(UiObjectI src,String sessionId) {
		super(TYPE, src);
		this.sessionId = sessionId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
}
