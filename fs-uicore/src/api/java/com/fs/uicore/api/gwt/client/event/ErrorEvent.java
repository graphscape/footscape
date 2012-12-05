/**
 * Jul 17, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ErrorEvent extends Event {

	public static final String MESSAGE = "message";

	public static final String CAUSE = "cause";

	public static Type TYPE = new Type();

	public static class Type extends Event.Type<ErrorEvent> {

	}

	public ErrorEvent(UiObjectI src, Throwable t) {
		super(TYPE, src);
		this.properties.setProperty(CAUSE, t);
	}

	/** */
	public ErrorEvent(UiObjectI src, String msg) {
		super(TYPE, src);
		this.properties.setProperty(MESSAGE, msg);
	}

	public String getMessage() {
		return (String) this.properties.getProperty(MESSAGE, null);
	}

	public Throwable getCause() {
		return (Throwable) this.properties.getProperty(CAUSE);
	}

}
