/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.google.gwt.event.dom.client.KeyCodes;

/**
 * @author wu
 * 
 */
public abstract class KeyCodeEvent extends Event {

	protected int keyCode;
	
	protected boolean ctlKey;
	/** */
	public KeyCodeEvent(Type<? extends KeyCodeEvent> type,UiObjectI src, int keyCode, boolean ctlKey) {
		super(type, src);
		this.keyCode = keyCode;
		this.ctlKey = ctlKey;
	}
	
	public int getNativeKeyCode() {
		return this.keyCode;
	}
	
	public boolean isEnter(){
		return KeyCodes.KEY_ENTER == this.keyCode;
	}
	
	public boolean isCtlKey(){
		return this.ctlKey;
	}

}
