/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class ScrollEvent extends Event {

	public static final Type<ScrollEvent> TYPE = new Type<ScrollEvent>("scroll");

	private Point topLeft;

	/**
	 * @param type
	 */
	public ScrollEvent(Point topLeft, UiObjectI src) {
		super(TYPE);
		this.topLeft = topLeft;
	}

	public Point getTopLeft() {
		return topLeft;
	}
}
