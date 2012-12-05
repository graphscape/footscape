/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public class ActionSuccessEvent extends ActionEvent {

	public static final Type<ActionSuccessEvent> TYPE = new Type<ActionSuccessEvent>(
			ActionEvent.TYPE);

	/**
	 * @param type
	 * @param control
	 * @param a
	 */
	public ActionSuccessEvent(ControlI control, String a) {
		super(TYPE, control, a);
	}

}
