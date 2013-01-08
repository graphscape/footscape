/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public abstract class ControlEvent extends Event {

	public static final Type<ControlEvent> TYPE = new Type<ControlEvent>("unknow");

	/**
	 * @param type
	 */
	public ControlEvent(Type<? extends Event> type, ControlI control) {
		super(type, control);
	}

	public ControlI getControl() {
		return (ControlI) this.source;
	}
}
