/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 26, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class AfterExpSearchEvent extends Event {

	public static final Type<AfterExpSearchEvent> TYPE = new Type<AfterExpSearchEvent>();

	private String expId;

	/**
	 * @param type
	 */
	public AfterExpSearchEvent(ExpSearchControlI src, String expId) {
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
