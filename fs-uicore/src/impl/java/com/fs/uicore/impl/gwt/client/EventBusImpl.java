/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.Event.FilterI;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
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

	@Override
	public <E extends Event> void addHandler(EventHandlerI<E> l) {
		this.eventDispatcher.addHandler((UiObjectI) null, l);
	}

	/* */
	@Override
	public <E extends Event> void addHandler(Type<E> ec, EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(null, ec, l);
	}

	@Override
	public <E extends Event> void addHandler(FilterI ef, EventHandlerI<E> eh) {
		this.eventDispatcher.addHandler(ef, eh);
	}

}
