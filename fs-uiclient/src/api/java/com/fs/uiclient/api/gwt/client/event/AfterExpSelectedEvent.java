/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wu
 * 
 */
public class AfterExpSelectedEvent extends Event {

	public static final Type<AfterExpSelectedEvent> TYPE = new Type<AfterExpSelectedEvent>();

	private String expId;

	/**
	 * @param type
	 */
	public AfterExpSelectedEvent(UiObjectI src, String expId) {
		super(TYPE, src);
		this.expId = expId;
	}

	/**
	 * @return the expId
	 */
	public String getExpId() {
		return expId;
	}

}
