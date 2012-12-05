/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.WindowI;

/**
 * @author wu
 * 
 */
public class WindowClosingEvent extends StateChangeEvent {

	public static final Type<WindowClosingEvent> TYPE = new Type<WindowClosingEvent>();

	/**
	 * @param type
	 */
	public WindowClosingEvent(WindowI window) {
		super(TYPE, window);
	}

}
