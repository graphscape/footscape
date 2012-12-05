/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 * 
 */
public abstract class ActionEvent extends ControlEvent {

	public static final Type<ActionEvent> TYPE = new Type<ActionEvent>(
			ControlEvent.TYPE);

	private String action;

	/**
	 * @param src
	 * @param code
	 */
	public ActionEvent(Type<? extends ActionEvent> type, ControlI control,
			String a) {
		super(type, control);
		this.action = a;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

}
