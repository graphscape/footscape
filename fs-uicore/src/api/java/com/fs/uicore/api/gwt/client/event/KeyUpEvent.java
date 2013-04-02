/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class KeyUpEvent extends KeyCodeEvent {

	public static final Type<KeyUpEvent> TYPE = new Type<KeyUpEvent>("key-up");

	/** */
	public KeyUpEvent(UiObjectI src, int keyCode, boolean ctlKey) {
		super(TYPE, src, keyCode, ctlKey);
	}

}
