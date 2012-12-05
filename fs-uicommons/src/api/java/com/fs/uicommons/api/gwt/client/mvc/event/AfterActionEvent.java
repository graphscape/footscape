/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.event;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wuzhen
 * 
 */
public class AfterActionEvent extends ActionEvent {

	public static final Type<AfterActionEvent> TYPE = new Type<AfterActionEvent>(
			ActionEvent.TYPE);

	private UiResponse response;

	/**
	 * @param src
	 * @param code
	 */
	public AfterActionEvent(ControlI c, String a, UiResponse res) {
		super(TYPE, c, a);
		this.response = res;
	}

	/**
	 * @return the response
	 */
	public UiResponse getResponse() {
		return response;
	}

}
