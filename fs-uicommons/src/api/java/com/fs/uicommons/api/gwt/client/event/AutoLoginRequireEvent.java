/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 * 
 */
public class AutoLoginRequireEvent extends Event {

	public static final Type<AutoLoginRequireEvent> TYPE = new Type<AutoLoginRequireEvent>("auto-login-request");

	/**
	 * @param src
	 * @param code
	 */
	public AutoLoginRequireEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
