/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wuzhen
 * 
 */
public class BeforeActionEvent extends ActionEvent {

	public static final Type<BeforeActionEvent> TYPE = new Type<BeforeActionEvent>(
			ActionEvent.TYPE);

	/**
	 * @param src
	 * @param code
	 */
	public BeforeActionEvent(ControlI control, String action) {
		super(TYPE, control, action);

	}

}
