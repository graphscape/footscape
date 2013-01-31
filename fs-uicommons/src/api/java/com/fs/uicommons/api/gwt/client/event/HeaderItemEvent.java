/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class HeaderItemEvent extends Event {

	public static final Type<HeaderItemEvent> TYPE = new Type<HeaderItemEvent>("header-item");

	protected Path path;

	/**
	 * @param type
	 */
	public HeaderItemEvent(UiObjectI src, Path path) {
		super(TYPE, src);
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

}
