/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wu
 * 
 */
public class ActionFailedEvent extends ActionEvent {

	public static final Type<ActionFailedEvent> TYPE = new Type<ActionFailedEvent>(
			ActionEvent.TYPE);
	protected ErrorInfosData errorInfosData;

	/**
	 * @param type
	 * @param control
	 * @param a
	 */
	public ActionFailedEvent(ControlI control, String a, ErrorInfosData eis) {
		super(TYPE, control, a);
		this.errorInfosData = eis;
	}

	/**
	 * @return the errorInfosData
	 */
	public ErrorInfosData getErrorInfosData() {
		return errorInfosData;
	}

}
