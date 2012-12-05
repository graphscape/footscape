/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class SizeChangeEvent extends Event {

	public static final Type<SizeChangeEvent> TYPE = new Type<SizeChangeEvent>();

	private Size size;

	/**
	 * @param type
	 */
	public SizeChangeEvent(Size size, UiObjectI src) {
		super(TYPE);
	}

	/**
	 * @return the size
	 */
	public Size getSize() {
		return size;
	}

}
