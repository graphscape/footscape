/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 * 
 */
public class ActionEvent extends Event {

	public static final Type<ActionEvent> TYPE = new Type<ActionEvent>("action");

	/**
	 * @param src
	 * @param code
	 */
	public ActionEvent(UiObjectI src, Path p) {
		super(TYPE, src, p);
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return this.getPath().getName();
	}

}
