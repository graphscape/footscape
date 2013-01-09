/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class SelectEvent extends Event {

	public static Type<SelectEvent> TYPE = new Type<SelectEvent>("select");

	private boolean selected;

	/** */
	public SelectEvent(UiObjectI source, boolean sel) {
		super(TYPE, source);
		this.selected = sel;
	}

	public boolean isSelected() {
		return selected;
	}
}
