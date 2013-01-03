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
public class ActionEvent extends ControlEvent {

	public static final Type<ActionEvent> TYPE = new Type<ActionEvent>(ControlEvent.TYPE);

	private String action;

	/**
	 * @param src
	 * @param code
	 */
	public ActionEvent(ControlI control, String a) {
		super(TYPE, control);
		this.action = a;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/*
	 *Jan 2, 2013
	 */
	@Override
	public String toString() {
		// 
		return super.toString()+",action:"+action;
	}
	
	

}
