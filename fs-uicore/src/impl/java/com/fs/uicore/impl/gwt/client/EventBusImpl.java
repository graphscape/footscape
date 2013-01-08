/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wu
 * 
 */
public class EventBusImpl extends UiObjectSupport implements EventBusI {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#getEventBus()
	 */
	@Override
	public EventBusI getEventBus(boolean force) {
		return this;
	}


}
