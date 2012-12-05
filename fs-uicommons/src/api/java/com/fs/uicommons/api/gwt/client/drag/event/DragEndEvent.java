/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.fs.uicommons.api.gwt.client.drag.event;

import com.fs.uicommons.api.gwt.client.drag.DragableI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class DragEndEvent extends DragEvent {

	public static Event.Type<DragEndEvent> TYPE = new Event.Type<DragEndEvent>(
			DragEvent.TYPE);

	/**
	 * @param type
	 */
	public DragEndEvent(DragableI source, DraggerI db, Point p) {
		super(TYPE, source, db, p);
	}

}
