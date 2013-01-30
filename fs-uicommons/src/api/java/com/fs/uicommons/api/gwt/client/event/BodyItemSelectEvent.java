/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class BodyItemSelectEvent extends BodyItemEvent {

	public static final Type<BodyItemSelectEvent> TYPE = new Type<BodyItemSelectEvent>(BodyItemEvent.TYPE,
			"select");

	protected boolean selected;

	/**
	 * @param type
	 */
	public BodyItemSelectEvent(BodyModelI bm, Path path, boolean sel) {
		super(TYPE, bm, path);
		this.path = path;
		this.selected = sel;
	}

	public boolean isSelected() {
		return selected;
	}

}
