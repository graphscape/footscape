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

	public static Type<ErrorEvent> TYPE = new Type<ErrorEvent>("error");

	public ErrorEvent(UiObjectI src, Throwable t) {
		super(TYPE, src);
		this.setProperty(CAUSE, t);
	}

	/** */
	public ErrorEvent(UiObjectI src, String msg) {
		super(TYPE, src);
		this.setProperty(MESSAGE, msg);
	}

	public String getErrorMessage() {
		return (String) this.getProperty(MESSAGE, null);
	}

	public Throwable getCause() {
		return (Throwable) this.getProperty(CAUSE);
	}

}
