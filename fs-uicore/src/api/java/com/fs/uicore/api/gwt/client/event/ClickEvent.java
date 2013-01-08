/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ClickEvent extends Event {

	public static final Type<ClickEvent> TYPE = new Type<ClickEvent>("click");

	/** */
	public ClickEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
