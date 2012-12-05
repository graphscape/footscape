/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.FilterI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.EventBusI#andHandler(com.fs.uicore.api.gwt
	 * .client.core.Event.FilterI,
	 * com.fs.uicore.api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public <E extends Event> void addHandler(FilterI ef, HandlerI<E> eh) {
		this.eventDispatcher.addHandler(ef,eh);
	}

}
