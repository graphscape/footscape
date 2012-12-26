/**
 *  Dec 26, 2012
 */
package com.fs.uiclient.impl.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class ExpCreatedEvent extends Event {

	public static final Event.Type<ExpCreatedEvent> TYPE = new Event.Type<ExpCreatedEvent>();

	public ExpCreatedEvent() {
		super(TYPE);
	}

}
