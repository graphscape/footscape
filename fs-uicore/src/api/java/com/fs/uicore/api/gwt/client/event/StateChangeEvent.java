/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class StateChangeEvent extends Event {

	public static final Event.Type<StateChangeEvent> TYPE = new Event.Type<StateChangeEvent>("state-change");

	/**
	 * @param type
	 */
	public StateChangeEvent(UiObjectI src) {
		this(TYPE, src);
	}

	public StateChangeEvent(Type<? extends StateChangeEvent> type, UiObjectI src) {
		super(type, src);
	}

	public static StateChangeEvent dispatch(UiObjectI src) {
		StateChangeEvent evt = new StateChangeEvent(src);
		return evt.dispatch();
	}

}
