/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 4, 2013
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class IncomingCrEvent extends Event {

	public static final Type<IncomingCrEvent> TYPE = new Type<IncomingCrEvent>();

	private String crId;

	public IncomingCrEvent(CooperControlI c, String crId) {
		super(TYPE, c);
		this.crId = crId;
	}

	/**
	 * @return the crId
	 */
	public String getCrId() {
		return crId;
	}

}
