/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class FocusEvent extends Event {

	public static final Type<FocusEvent> TYPE = new Type<FocusEvent>("focus");

	/** */
	public FocusEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
