/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class HideEvent extends Event {

	public static final Type<HideEvent> TYPE = new Type<HideEvent>("hide");

	/**
	 * @param type
	 */
	public HideEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
