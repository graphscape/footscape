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
public class BlurEvent extends Event {

	public static final Type<BlurEvent> TYPE = new Type<BlurEvent>("blur");

	/** */
	public BlurEvent(UiObjectI src) {
		super(TYPE, src);
	}

}
