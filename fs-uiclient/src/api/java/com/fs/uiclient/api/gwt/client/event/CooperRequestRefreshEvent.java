/**
 *  Dec 27, 2012
 */
package com.fs.uiclient.api.gwt.client.event;

import com.fs.uiclient.api.gwt.client.coper.CooperControlI;
import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class CooperRequestRefreshEvent extends Event {

	public static final Type<CooperRequestRefreshEvent> TYPE = new Type<CooperRequestRefreshEvent>();

	private String cooperRequestId;

	/**
	 * @param type
	 */
	public CooperRequestRefreshEvent(CooperControlI src, String crId) {
		super(TYPE, src);
		this.cooperRequestId = crId;
	}

	public String getExpId() {
		return cooperRequestId;
	}

}
