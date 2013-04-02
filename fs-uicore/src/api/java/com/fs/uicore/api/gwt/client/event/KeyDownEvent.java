/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class KeyDownEvent extends KeyCodeEvent {

	public static final Type<KeyDownEvent> TYPE = new Type<KeyDownEvent>("key-down");

	/** */
	public KeyDownEvent(UiObjectI src, int keyCode, boolean ctr) {
		super(TYPE, src, keyCode, ctr);
	}

}
