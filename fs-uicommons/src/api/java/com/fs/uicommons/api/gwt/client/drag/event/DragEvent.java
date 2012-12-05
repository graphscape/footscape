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
public abstract class DragEvent extends Event {

	public static Event.Type<DragEvent> TYPE = new Event.Type<DragEvent>();

	protected DraggerI dragger;

	protected Point point;// mouse point

	/**
	 * @param type
	 */
	public DragEvent(Event.Type<? extends DragEvent> type, DragableI source,
			DraggerI db, Point point) {
		super(type, source);
		this.dragger = db;
		this.point = point;
	}

	public DraggerI getDragger() {
		return dragger;
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "Event,class:" + this.getClass().getName() + ",point:"+this.point;
	}
}
