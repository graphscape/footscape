/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.event.ModelEvent;

/**
 * @author wuzhen
 * 
 */
public class LoginCreatedEvent extends ModelEvent {

	public static final Type<LoginCreatedEvent> TYPE = new Type<LoginCreatedEvent>("login-created");

	/**
	 * @param src
	 */
	public LoginCreatedEvent(ModelI src) {
		super(TYPE, src);
	}

}
